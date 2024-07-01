package ktc.nhom1ktc.service;

import ktc.nhom1ktc.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IUserService {
    Users getByID(UUID id);
    Users create(Users user);
    Users update(UUID id, Users user);
    void delete(UUID id);
    List<Users> getAll();
    Page<Users> getAllPageable(Pageable pageable);
    void open(UUID id);
}
