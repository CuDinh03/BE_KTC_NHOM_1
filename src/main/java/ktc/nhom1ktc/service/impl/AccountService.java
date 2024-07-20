package ktc.nhom1ktc.service.impl;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import ktc.nhom1ktc.dto.AuthenticationRequest;
import ktc.nhom1ktc.dto.AuthenticationResponse;
import ktc.nhom1ktc.dto.ChangePasswordRequest;
import ktc.nhom1ktc.entity.Account;
import ktc.nhom1ktc.entity.Role;
import ktc.nhom1ktc.entity.Users;
import ktc.nhom1ktc.exception.AppException;
import ktc.nhom1ktc.exception.ErrorCode;
import ktc.nhom1ktc.repository.AccountRepository;
import ktc.nhom1ktc.repository.RoleRepository;
import ktc.nhom1ktc.repository.UserRepository;
import ktc.nhom1ktc.service.IAccountService;
import ktc.nhom1ktc.service.IService;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class AccountService implements IService<Account>, IAccountService<Account> {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailSenderService emailSenderService;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Account getByID(UUID id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public Account create(Account account) {
        return null;
    }

    @Override
    public Account create(Account request, String mail) {
        // Kiểm tra nếu tên người dùng đã tồn tại
        if (accountRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.ACCOUNT_EXISTED);
        }

        // Kiểm tra nếu email đã tồn tại
        if (userRepository.existsByEmail(mail)) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }

        Account account = new Account();
        account.setId(UUID.randomUUID());
        account.setUsername(request.getUsername());
        account.setCode("Tk" + request.getUsername());
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        Role role = roleRepository.findByName("USER").get();
        account.setRole(role);
        account.setCreatedAt(new Date());
        account.setUpdateAt(new Date());
        account.setCreatedBy(request.getCreatedBy());
        account.setUpdatedBy(request.getUpdatedBy());
        account.setStatus(1);

        Account savedAccount = accountRepository.save(account);
        Users user = new Users();
        user.setEmail(mail);
        user.setId(UUID.randomUUID());
        user.setAccount(savedAccount);
        user.setCreatedAt(new Date());
        user.setCreatedBy(account.getUsername());
        user.setUpdateAt(new Date());
        user.setUpdatedBy(account.getUsername());
        userRepository.save(user);

        emailSenderService.sendAccountCreationEmail(mail);

        return savedAccount;
    }


    @Override
    public Account update(UUID uuid, Account account) {
        if (accountRepository.existsById(uuid)) {
            account.setId(uuid);
            return accountRepository.save(account);
        }
        return null;
    }

    @Override
    public void delete(UUID id) {
        accountRepository.deleteById(id);
    }

    @Override
    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    @Override
    public Page<Account> getAllPageable(Pageable pageable) {
        return accountRepository.findAll(pageable);
    }

    @Override
    public Optional<Account> findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    @NonFinal

    @Value("${jwt.signerKey}")
    private String signerKey;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        String inputRequest = request.getInput();
        Account account;
        Users users;

        if (inputRequest.contains("@")) {
            users = userRepository.findByEmail(inputRequest)
                    .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_EXISTED));
            account = users.getAccount();
        } else {
            account = accountRepository.findByUsername(inputRequest)
                    .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_EXISTED));
            users = userRepository.findByIdAccount(account.getId()).get();
        }

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(request.getPassword(), account.getPassword());

        if (!authenticated)
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        var token = generateToken(account);

        return AuthenticationResponse.builder()
                .accountId(String.valueOf(account.getId()))
                .mail(users.getEmail())
                .userId(String.valueOf(users.getId()))
                .username(account.getUsername())
                .name(users.getFirstName())
                .lastName(users.getLastName())
                .token(token)
                .authenticated(true)
                .build();
    }


    private String generateToken(Account account) {

        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(account.getUsername())
                .issuer(account.getUsername())
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(3, ChronoUnit.HOURS).toEpochMilli()
                ))
                .claim("scope", buildScope(account))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);
        try {

            jwsObject.sign(new MACSigner(signerKey));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            System.out.println("cannot create token");
            throw new RuntimeException();
        }

    }

    private String buildScope(Account account) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (account.getRole() != null && account.getRole().getName() != null) {
            List<String> nameList = Collections.singletonList(account.getRole().getName());
            for (String role : nameList) {
                stringJoiner.add(role);
            }
        }
        return stringJoiner.toString();
    }


    public boolean changePassword(ChangePasswordRequest request) {
        Account account = accountRepository.findById(request.getAccountId()).orElse(null);
        if (account == null) {
            return false;
        }
        if (!passwordEncoder.matches(request.getOldPassword(), account.getPassword())) {
            return false;
        }
        account.setPassword(passwordEncoder.encode(request.getNewPassword()));
        accountRepository.save(account);
        return true;
    }
}
