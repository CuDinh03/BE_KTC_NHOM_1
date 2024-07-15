package ktc.nhom1ktc.service;

import java.util.Optional;

public interface IAccountService<T> {

    Optional<T> findByUsername(String username);
}
