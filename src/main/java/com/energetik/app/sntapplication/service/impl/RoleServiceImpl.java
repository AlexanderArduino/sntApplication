package com.energetik.app.sntapplication.service.impl;


import com.energetik.app.sntapplication.repository.RoleRepository;
import com.energetik.app.sntapplication.security.Role;
import com.energetik.app.sntapplication.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Role> findRoleById(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Role> findRoleByRolename(String rolename) {
        return roleRepository.findByRolename(rolename);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existRoleById(Long id) {
        return roleRepository.existsById(id);
    }

    @Override
    public void saveRole(Role role) {
        if (role == null || role.getRolename() == null) {
            throw new IllegalArgumentException("Role or role.rolename must not be null");
        }
        Optional<Role> optRole = roleRepository.findByRolename(role.getRolename());
        if (optRole.isEmpty()) {
            roleRepository.saveAndFlush(role);
        } else {
            throw new IllegalArgumentException(
                    String.format("Role with rolename: %s was exist", role.getRolename())
            );
        }
    }

    @Override
    public void updateRole(Role role) {
        if (role == null || role.getRolename() == null) {
            throw new IllegalArgumentException("Role or role.rolename must not be null");
        }
        Optional<Role> optRole = roleRepository.findByRolename(role.getRolename());
        if (optRole.isPresent()) {
            roleRepository.saveAndFlush(role);
        } else {
            throw new IllegalArgumentException(
                    String.format("Role with rolename: %s was not exist", role.getRolename())
            );
        }
    }

    @Override
    public void deleteRoleById(Long id) {
        if(id == null) {
            throw new IllegalArgumentException("Role.id must not be null");
        }
        Optional<Role> optRole = roleRepository.findById(id);
        if(optRole.isEmpty()) {
            throw new IllegalArgumentException(
                    String.format("Role with id:%d was not exist", id)
            );
        } else {
            roleRepository.deleteById(id);
        }
    }
}
