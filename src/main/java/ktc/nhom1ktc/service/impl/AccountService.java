package ktc.nhom1ktc.service.impl;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import ktc.nhom1ktc.dto.AuthenticationRequest;
import ktc.nhom1ktc.dto.AuthenticationResponse;
import ktc.nhom1ktc.entity.Account;
import ktc.nhom1ktc.exception.AppException;
import ktc.nhom1ktc.exception.ErrorCode;
import ktc.nhom1ktc.repository.AccountRepository;
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
public class AccountService implements IService<Account>, IAccountService {

    @Autowired
    AccountRepository accountRepository;
    @Override
    public Account getByID(UUID id) {
        return null;
    }

    @Override
    public Account create(Account request) {
        return accountRepository.save(request);
    }

    @Override
    public Account update(UUID uuid, Account account) {
        return null;
    }

    @Override
    public void delete(UUID id) {

    }

    @Override
    public List<Account> getAll() {
        return null;
    }

    @Override
    public Page<Account> getAllPageable(Pageable pageable) {
        return null;
    }

    @NonFinal

    @Value("${jwt.signerKey}")
    private String signerKey;
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var account = accountRepository.findByUsername(request.getUsername()).orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_EXISTED));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(request.getPassword(), account.getPassword());

        if (!authenticated)
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        var token = generateToken(account);

        return AuthenticationResponse.builder()
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
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
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
}
