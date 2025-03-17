package com.energetik.app.sntapplication.service;

import com.energetik.app.sntapplication.security.Role;

import java.util.Optional;

public interface RoleService {

    Optional<Role> findRoleById(Long id);

    Optional<Role> findRoleByRolename(String rolename);

    boolean existRoleById(Long id);

    void saveRole(Role role);

    void updateRole(Role role);

    void deleteRoleById(Long id);

}