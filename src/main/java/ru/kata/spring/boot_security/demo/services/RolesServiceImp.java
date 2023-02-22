package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class RolesServiceImp implements RolesService {
    private final RoleRepository roleRepository;

    @Autowired
    public RolesServiceImp(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    public Role findRoleById(long id) {
        Optional<Role> findRole = roleRepository.findById(id);
        return findRole.orElse(null);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
