package com.energetik.app.sntapplication.service;

import com.energetik.app.sntapplication.entity.Gardener;
import com.energetik.app.sntapplication.entity.Role;
import com.energetik.app.sntapplication.repository.GardenerRepository;
import com.energetik.app.sntapplication.service.impl.GardenerServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class GardenerServiceTest {

//    @Container
//    private static final PostgreSQLContainer<?> container =
//            new PostgreSQLContainer<>("postgres:17")
//                    .withDatabaseName("testdb")
//                    .withUsername("test")
//                    .withPassword("test");

    @Autowired
    private GardenerServiceImpl gardenerService;

    @Autowired
    private GardenerRepository gardenerRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    @DynamicPropertySource
//    static void postgresProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url", container::getJdbcUrl);
//        registry.add("spring.datasource.username", container::getUsername);
//        registry.add("spring.datasource.password", container::getPassword);
//    }

    @Test
    @DisplayName("Поиск пользователя по ID")
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            value = "/script/service/GardenerServiceTest/before.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = "/script/service/GardenerServiceTest/after.sql")
    void findGardenerByIdTest() throws Exception {
        Optional<Gardener> optionalGardener = gardenerService.findGardenerById(1L);
        System.out.println(optionalGardener);
        assertTrue(optionalGardener.isPresent(), "Пользователь с id=1 не найден");
        assertEquals("admin", optionalGardener.get().getUsername(),
                "Имя найденного пользователя не совпадает с искомым");

        optionalGardener = gardenerService.findGardenerById(999L);
        assertTrue(optionalGardener.isEmpty(), "Получение несуществующего пользователя с id=999");
    }

    @Test
    @DisplayName("Поиск пользователя по username")
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            value = "/script/service/GardenerServiceTest/before.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = "/script/service/GardenerServiceTest/after.sql")
    void findGardenerByUsernameTest() {
        Optional<Gardener> optionalGardener = gardenerRepository.findGardenerByUsername("admin");
        assertTrue(optionalGardener.isPresent(), "");
        assertEquals("admin", optionalGardener.get().getUsername(), "");

        optionalGardener = gardenerRepository.findGardenerByUsername("abcd");
        assertTrue(optionalGardener.isEmpty(), "Найден не существующий пользователь");
    }



    @Test
    @DisplayName("Сохранение нового пользователя")
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            value = "/script/service/GardenerServiceTest/beforeSaveTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = "/script/service/GardenerServiceTest/after.sql")
    void saveGardenerTest() {
        Gardener testGardenerNull = null;
        assertThrows(IllegalArgumentException.class,
                () -> gardenerService.saveGardener(testGardenerNull));
        Gardener testGardenerUsernameNull = new Gardener();
        testGardenerUsernameNull.setNote("тестовый пользователь");
        assertThrows(IllegalArgumentException.class,
                () -> gardenerService.saveGardener(testGardenerUsernameNull));

        Gardener saveGard = new Gardener();
        saveGard.setUsername("testingUsername");
        saveGard.setPassword(passwordEncoder.encode("testingPassword"));
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(roleService.findRoleByRolename("ADMIN").get());
        roleSet.add(roleService.findRoleByRolename("USER").get());
        saveGard.setRoles(roleSet);
        saveGard.setFirstName("testingFirstName");
        saveGard.setMiddleName("testingMiddleName");
        saveGard.setLastName("testingLastName");
        saveGard.setPhone("testingPhone");
        saveGard.setEmail("testingEmail");
        saveGard.setMember(true);
        saveGard.setDateEnter(LocalDateTime.now());
        saveGard.setDocumentEnter("testingDocumentEnter");
        saveGard.setDateOut(LocalDateTime.now());
        saveGard.setReasonOut("testingReasonOut");
        saveGard.setPassport("testingPassport");
        saveGard.setAddressRegistration("testingAddressRegistration");
        saveGard.setAddressResidential("testingAddressResidential");
        saveGard.setNote("testingNote");
        saveGard.setArchive(true);
        gardenerService.saveGardener(saveGard);
        Optional<Gardener> opt = gardenerService.findGardenerByUsername("testingUsername");
        assertTrue(opt.isPresent());
        assertEquals("testingNote",opt.get().getNote());

        assertThrows(IllegalArgumentException.class,
                () -> gardenerService.saveGardener(saveGard));
    }


    @Test
    @DisplayName("Обновление данных пользователя")
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            value = "/script/service/GardenerServiceTest/before.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = "/script/service/GardenerServiceTest/after.sql")
    void updateGardenerTest() {
        assertThrows(IllegalArgumentException.class,
                () -> gardenerService.updateGardener("admin",null));
        assertThrows(IllegalArgumentException.class,
                () -> gardenerService.updateGardener(null, new Gardener()));

        Optional<Gardener> optionalGardener =
                gardenerService.findGardenerById(1L);
        Gardener nGard = optionalGardener.get();
        String username = nGard.getUsername();
        nGard.setUsername("Ivan");
        nGard.setNote("testing change");
        gardenerService.updateGardener(username, nGard);
        Optional<Gardener> checkGard = gardenerService.findGardenerByUsername("Ivan");
        assertTrue(checkGard.isPresent());
        assertEquals("Ivan", checkGard.get().getUsername());
        assertEquals("testing change", checkGard.get().getNote());
    }


    @Test
    @DisplayName("Проверка наличия пользователя")
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            value = "/script/service/GardenerServiceTest/before.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = "/script/service/GardenerServiceTest/after.sql")
    void existGardenerById() {
        assertTrue(gardenerService.existGardenerById(1L));
        assertFalse(gardenerService.existGardenerById(999L));
    }


    @Test
    @DisplayName("Удаление пользователя по ID")
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            value = "/script/service/GardenerServiceTest/before.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = "/script/service/GardenerServiceTest/after.sql")
    void deleteGardenerById() {
        assertThrows(IllegalArgumentException.class,
                () -> gardenerService.deleteGardenerById(null));
        assertTrue(gardenerService.existGardenerById(1L));
        gardenerService.deleteGardenerById(1L);
        assertFalse(gardenerService.existGardenerById(1L));
        assertThrows(IllegalArgumentException.class,
                () -> gardenerService.deleteGardenerById(1L));
    }
}

