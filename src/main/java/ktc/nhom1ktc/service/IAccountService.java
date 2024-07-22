package ktc.nhom1ktc.service;

import ktc.nhom1ktc.entity.Account;
import java.util.Optional;

public interface IAccountService<T> {
    Account create(Account request, String mail);
    Optional<T> findByUsername(String username);

}
