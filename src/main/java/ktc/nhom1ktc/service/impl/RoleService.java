package ktc.nhom1ktc.service.impl;

import ktc.nhom1ktc.entity.Role;
import ktc.nhom1ktc.repository.RoleRepository;
import ktc.nhom1ktc.service.IRole;
import ktc.nhom1ktc.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
public class RoleService implements IService<Role>, IRole {

    @Autowired
    RoleRepository repository;
    @Override
    public Role getByID(UUID id) {
        return null;
    }

    @Override
    public Role create(Role role) {
        return repository.save(role);
    }

    @Override
    public Role update(UUID uuid, Role role) {
        return null;
    }

    @Override
    public void delete(UUID id) {

    }

    @Override
    public List<Role> getAll() {
        return null;
    }

    @Override
    public Page<Role> getAllPageable(Pageable pageable) {
        return null;
    }
}
