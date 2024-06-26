package ktc.nhom1ktc.controller;

import ktc.nhom1ktc.dto.ApiResponse;
import ktc.nhom1ktc.dto.AuthenticationRequest;
import ktc.nhom1ktc.dto.AuthenticationResponse;
import ktc.nhom1ktc.service.impl.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    @Autowired
    private AccountService accountService;
    @PostMapping("/log-in")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
       var result = accountService.authenticate(request);
       return ApiResponse.<AuthenticationResponse>builder()
               .code(1000)
               .result(result)
               .build();
    }

}
