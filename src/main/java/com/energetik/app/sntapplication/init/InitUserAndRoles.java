package com.energetik.app.sntapplication.init;


import com.energetik.app.sntapplication.security.Role;
import com.energetik.app.sntapplication.security.User;
import com.energetik.app.sntapplication.service.RoleService;
import com.energetik.app.sntapplication.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Component
@Profile("dev")
public class InitUserAndRoles {

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public InitUserAndRoles(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @PostConstruct
    @Transactional
    public void init() {
        Role userRole = new Role();
        userRole.setRolename("USER");
        roleService.saveRole(userRole);

        Role adminRole = new Role();
        adminRole.setRolename("ADMIN");
        roleService.saveRole(adminRole);


        User user = new User();
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("user"));
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(roleService.findRoleByRolename("USER").orElse(null));
        user.setRoles(userRoles);

        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin"));
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(roleService.findRoleByRolename("ADMIN").orElse(null));
        admin.setRoles(adminRoles);

        userService.saveUser(user);
        userService.saveUser(admin);
    }
}
