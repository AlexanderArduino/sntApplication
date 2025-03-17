package com.energetik.app.sntapplication.init;


import com.energetik.app.sntapplication.entity.Gardener;
import com.energetik.app.sntapplication.entity.Role;
import com.energetik.app.sntapplication.service.RoleService;
import com.energetik.app.sntapplication.service.GardenerService;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Component
@Profile("dev")
public class InitUserAndRoles {

    private final GardenerService gardenerService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public InitUserAndRoles(GardenerService gardenerService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.gardenerService = gardenerService;
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


        Gardener gardener = new Gardener();
        gardener.setUsername("user");
        gardener.setPassword(passwordEncoder.encode("user"));
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(roleService.findRoleByRolename("USER").orElse(null));
        gardener.setRoles(userRoles);

        Gardener admin = new Gardener();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin"));
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(roleService.findRoleByRolename("ADMIN").orElse(null));
        admin.setRoles(adminRoles);

        Gardener gardener1 = new Gardener();
        gardener1.setUsername("aleks");
        gardener1.setPassword(passwordEncoder.encode("aleks"));
        gardener1.setRoles(adminRoles);
        gardener1.setFirstName("Aleksander");
        gardener1.setMiddleName("Igorevich");
        gardener1.setLastName("Anohin");
        gardener1.setPhone("+71234567890");
        gardener1.setEmail("aleks@gmail.com");
        gardener1.setMember(true);
        gardener1.setDateEnter(LocalDateTime.of(2000, 1, 1, 12, 0, 0));
        gardener1.setDocumentEnter("Заявление номер 1");
        gardener1.setDateOut(null);
        gardener1.setReasonOut(null);
        gardener1.setPassport("4001 123456");
        gardener1.setAddressRegistration("Санкт-Петербург, ул.Неизвестная, д1, корп.1, кв.1");
        gardener1.setAddressResidential("Санкт-Петербург, ул.Неизвестная, д1, корп.1, кв.1");
        gardener1.setNote("Пытаюсь сделать рабочую программу");
        gardener1.setArchive(false);

        gardenerService.saveGardener(gardener);
        gardenerService.saveGardener(gardener1);
        gardenerService.saveGardener(admin);
    }
}
