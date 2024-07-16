package ktc.nhom1ktc.controller;

import ktc.nhom1ktc.dto.ApiResponse;
import ktc.nhom1ktc.dto.AuthenticationRequest;
import ktc.nhom1ktc.dto.AuthenticationResponse;
import ktc.nhom1ktc.dto.ResetPasswordRequest;
import ktc.nhom1ktc.entity.PasswordResetToken;
import ktc.nhom1ktc.entity.Users;
import ktc.nhom1ktc.exception.AppException;
import ktc.nhom1ktc.exception.ErrorCode;
import ktc.nhom1ktc.repository.PasswordResetTokenRepository;
import ktc.nhom1ktc.repository.UserRepository;
import ktc.nhom1ktc.service.impl.AccountService;
import ktc.nhom1ktc.service.impl.EmailSenderService;
import ktc.nhom1ktc.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;
    @PostMapping("/log-in")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
       var result = accountService.authenticate(request);
       return ApiResponse.<AuthenticationResponse>builder()
               .code(1000)
               .result(result)
               .build();
    }

    @PostMapping("/reset-password")
    public ApiResponse<Users> resetPassword(@RequestBody ResetPasswordRequest request) {
        ApiResponse<Users> apiResponse = new ApiResponse<>();

        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(request.getToken())
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_TOKEN));

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new AppException(ErrorCode.EXPIRED_TOKEN);
        }

        Users user = resetToken.getUser();
        user.getAccount().setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        apiResponse.setCode(1000);
        apiResponse.setResult(user);
        apiResponse.setMessage("Password has been successfully changed.");

        // Xóa mã thông báo sau khi đặt lại mật khẩu thành công
        passwordResetTokenRepository.delete(resetToken);

        return apiResponse;
    }

    @PostMapping("/forgot-password")
    public ApiResponse<Users> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        if (email == null || email.isEmpty()) {
            return ApiResponse.<Users>builder().message("error").build() ;
        }

        ApiResponse<Users> apiResponse = new ApiResponse<>();

        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.EMAIL_NOT_FOUND));

        PasswordResetToken token = new PasswordResetToken();
        token.setToken(UUID.randomUUID().toString());
        token.setUser(user);
        token.setExpiryDate(LocalDateTime.now().plusMinutes(5)); // Token thông báo hết hạn sau 5 phút
        passwordResetTokenRepository.save(token);

        apiResponse.setCode(1000);
        apiResponse.setResult(user);
        apiResponse.setMessage("Password reset link has been sent to your email.");

        emailSenderService.sendPasswordResetEmail(user.getEmail(), token.getToken());

        return apiResponse;
    }


}
