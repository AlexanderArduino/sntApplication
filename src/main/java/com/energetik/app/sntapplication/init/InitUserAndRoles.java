package com.energetik.app.sntapplication.init;


import com.energetik.app.sntapplication.entity.*;
import com.energetik.app.sntapplication.service.*;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
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
    private final ConversationService conversationService;
    private final DebtorsService debtorsService;

    private final PaymentService paymentService;

    public InitUserAndRoles(GardenerService gardenerService, RoleService roleService,
                            ConversationService conversationService, DebtorsService debtorsService,
                            PaymentService paymentService) {
        this.gardenerService = gardenerService;
        this.roleService = roleService;
        this.conversationService = conversationService;
        this.debtorsService = debtorsService;
        this.paymentService = paymentService;
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


        Gardener user = new Gardener();
        user.setUsername("user");
        user.setPassword("user");
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(roleService.findRoleByRolename("USER").orElse(null));
        user.setRoles(userRoles);

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

        gardenerService.saveGardener(user);
        gardenerService.saveGardener(gardener1);
        gardenerService.saveGardener(admin);

        Payment payment1 = new Payment();
        payment1.setSumma(9500d);
        payment1.setDateOfPayment(LocalDateTime.of(2001, 1, 1, 12, 0, 0));
        payment1.setPeriodFrom(LocalDateTime.of(2001, 1, 1, 0, 0, 0));
        payment1.setPeriodTo(LocalDateTime.of(2001, 12, 31, 23, 59, 59));
        payment1.setTargetPay(true);
        payment1.setElectricityPay(false);
        payment1.setCash(false);
        payment1.setGardener(gardenerService.findGardenerByUsername("aleks").orElse(null));

        paymentService.savePayment(payment1);

        Conversation conversation1 = new Conversation();
        conversation1.setDate(LocalDateTime.of(2023, 1, 1, 12, 0, 0));
        conversation1.setIs_in_call(true);
        conversation1.setIs_out_call(false);
        conversation1.setReasone("Продажа участка");
        conversation1.setAbout("Продает участок. Про долг за 22 год знает. Обещал погасить");
        conversation1.setGardener(gardenerService.findGardenerById(2L).orElse(null));
        conversationService.saveConversation(conversation1);

        Debtors debtors1 = new Debtors();
        debtors1.setOn_date(LocalDateTime.of(2023, 12,31,23,59,59));
        debtors1.setBalance_member_pay(-123.00);
        debtors1.setBalance_electricity_pay(5000.00);
        debtors1.setGardener(gardenerService.findGardenerById(2L).orElse(null));
        debtors1.setTo_exclusion(true);
        debtors1.setExclude(false);
        debtors1.setNote("Пометки");
        debtorsService.saveDebtors(debtors1);

    }
}
