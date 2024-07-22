package ktc.nhom1ktc.service.impl;

import ktc.nhom1ktc.entity.Users;
import ktc.nhom1ktc.repository.UserRepository;
import ktc.nhom1ktc.service.IService;
import ktc.nhom1ktc.service.IUserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService implements IService<Users>, IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Users getByID(UUID id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public Users create(Users user) {
        return userRepository.save(user);
    }

    @Override
    public Users update(UUID id, Users user) {
        if (userRepository.existsById(id)) {
            user.setId(id);
            return userRepository.save(user);
        }
        return null;
    }

    @Override
    public void delete(UUID id) {
        Users user = getByID(id);
        user.setStatus(0);
        userRepository.saveAndFlush(user);
    }

    @Override
    public void open(UUID id) {
        Users user = getByID(id);
        user.setStatus(1);
        userRepository.saveAndFlush(user);

    }

    @Override
    public List<Users> getAll() {
        return userRepository.findAll();
    }

    @Override
    public Page<Users> getAllPageable(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
}
