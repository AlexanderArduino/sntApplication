package com.energetik.app.sntapplication.init;


import com.energetik.app.sntapplication.entity.Gardener;
import com.energetik.app.sntapplication.entity.Payment;
import com.energetik.app.sntapplication.entity.Role;
import com.energetik.app.sntapplication.service.PaymentService;
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

    private final PaymentService paymentService;

    public InitUserAndRoles(GardenerService gardenerService, RoleService roleService,
                            PasswordEncoder passwordEncoder, PaymentService paymentService) {
        this.gardenerService = gardenerService;
        this.roleService = roleService;
        this.paymentService = paymentService;
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
        gardener.setPassword("user");
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(roleService.findRoleByRolename("USER").orElse(null));
        gardener.setRoles(userRoles);

        Gardener admin = new Gardener();
        admin.setUsername("admin");
        admin.setPassword("admin");
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(roleService.findRoleByRolename("ADMIN").orElse(null));
        admin.setRoles(adminRoles);

        Gardener gardener1 = new Gardener();
        gardener1.setUsername("aleks");
        gardener1.setPassword("aleks");
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

        Payment payment1 = new Payment();
        payment1.setSumma(9500d);
        payment1.setDateOfPayment(LocalDateTime.of(2001,1,1,12,0,0));
        payment1.setPeriodFrom(LocalDateTime.of(2001, 1, 1, 0,0,0));
        payment1.setPeriodTo(LocalDateTime.of(2001, 12, 31, 23,59,59));
        payment1.setTargetPay(true);
        payment1.setElectricityPay(false);
        payment1.setCash(false);
        payment1.setGardener(gardenerService.findGardenerByUsername("aleks").orElse(null));

        paymentService.savePayment(payment1);
    }
}
