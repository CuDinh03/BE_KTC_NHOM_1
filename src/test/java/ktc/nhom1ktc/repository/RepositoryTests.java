package ktc.nhom1ktc.repository;

import ktc.nhom1ktc.entity.Account;
import ktc.nhom1ktc.entity.Role;
import ktc.nhom1ktc.repository.expense.management.category.CategoryRepository;
import ktc.nhom1ktc.service.impl.AccountUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class RepositoryTests {

    @MockBean
    protected AccountUtil accountUtil;

    @Autowired
    protected TestEntityManager entityManager;

    @Autowired
    protected CategoryRepository categoryRepository;

    @Autowired
    protected AccountRepository accountRepository;

    @Autowired
    protected RoleRepository roleRepository;

    protected PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    protected enum RoleType {
        ADMIN,
        USER,
    }

    protected final String codePrefix = "jpa_test_";

    protected LocalDateTime dateTime = LocalDateTime.now();
    protected final Date date = Date.from(dateTime.toLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant());

    protected Role userRole = makeRole(RoleType.USER, date);
    protected Role adminRole = makeRole(RoleType.ADMIN, date);

    protected final String normalUsername = codePrefix + "user";
    protected String adminUsername = codePrefix + "admin";

    protected Account userAcc = makeAccount(normalUsername, userRole, date);
    protected Account adminAcc = makeAccount(adminUsername, adminRole, date);


    protected static final String personal_username = "personal_username";

    protected Role makeRole(final RoleType role, final Date date) {
        return Role.builder()
                .name(role.name())
                .createdAt(date)
                .updateAt(date)
                .status(1)
                .build();
    }

    protected Role saveRole(final Role role) {
        return roleRepository.save(role);
    }

    protected Account makeAccount(final String username, final Role role, final Date date) {
        return Account.builder()
                .username(username)
                .code(username)
                .createdAt(date)
                .updateAt(date)
                .role(role)
                .status(1)
                .password(passwordEncoder.encode(username))
                .build();
    }

    protected Account saveAccount(final Account account) {
        return accountRepository.save(account);
    }

    @BeforeEach
    public void before() {
        roleRepository.findByName(RoleType.USER.name()).ifPresentOrElse(
                role -> userAcc = makeAccount(normalUsername, role, date),
                () -> saveRole(userRole));
        roleRepository.findByName(RoleType.ADMIN.name()).ifPresentOrElse(
                role -> adminAcc = makeAccount(adminUsername, role, date),
                () -> saveRole(adminRole));

        accountRepository.findByUsername(normalUsername).ifPresentOrElse(
                acc -> { acc.setRole(userRole);
                    userAcc = accountRepository.save(acc); },
                () -> userAcc = saveAccount(userAcc));
        accountRepository.findByUsername(adminUsername).ifPresentOrElse(
                acc -> { acc.setRole(adminRole);
                    adminAcc = accountRepository.save(acc); },
                () -> adminAcc = saveAccount(adminAcc));
    }
}
