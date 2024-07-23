package ktc.nhom1ktc.configuration;

import ktc.nhom1ktc.entity.Account;
import ktc.nhom1ktc.entity.Role;
import ktc.nhom1ktc.entity.Users;
import ktc.nhom1ktc.event.AdminInitEvent;
import ktc.nhom1ktc.repository.AccountRepository;
import ktc.nhom1ktc.repository.RoleRepository;
import ktc.nhom1ktc.service.impl.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;
    ApplicationEventPublisher applicationEventPublisher;
    UserService userService;

    @Bean
    ApplicationRunner applicationRunner(AccountRepository repository) {

        final String adminUsername = "admin";

        return args -> {
            if (repository.findByUsername(adminUsername).isEmpty()) {
                if (roleRepository.findByName("ADMIN").isEmpty()) {
                    Role role = Role.builder()
                            .name("ADMIN")
                            .createdAt(new Date())
                            .updateAt(new Date())
                            .createdBy(adminUsername)
                            .updatedBy(adminUsername)
                            .status(1)
                            .build();
                    roleRepository.save(role);
                    log.warn("ADMIN Role has been created!");

                }
                if (roleRepository.findByName("USER").isEmpty()) {
                    Role role = Role.builder()
                            .name("USER")
                            .createdAt(new Date())
                            .updateAt(new Date())
                            .createdBy(adminUsername)
                            .updatedBy(adminUsername)
                            .status(1)
                            .build();
                    roleRepository.save(role);
                    log.warn("USER Role has been created!");

                }

                Role role = roleRepository.findByName("ADMIN").get();
                Account account = Account.builder()
                        .username(adminUsername)
                        .code("TK01")
                        .createdAt(new Date())
                        .updateAt(new Date())
                        .createdBy(adminUsername)
                        .updatedBy(adminUsername)
                        .role(role)
                        .status(1)
                        .password(passwordEncoder.encode("admin"))
                        .build();

                Users users = Users.builder()
                        .account(repository.save(account))
                        .createdAt(new Date())
                        .createdBy("ADMIN")
                        .updateAt(new Date())
                        .status(1)
                        .build();
                userService.create(users);
                log.warn("admin user has been created with default password: admin, change it !! ");
            }

            applicationEventPublisher.publishEvent(new AdminInitEvent("source", adminUsername));
        };
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

