package ktc.nhom1ktc.repository;

import ktc.nhom1ktc.entity.Account;
import ktc.nhom1ktc.entity.Role;
import ktc.nhom1ktc.repository.expense.management.CategoryRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class RepositoryTests {

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

}
