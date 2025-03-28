package com.energetik.app.sntapplication.service;


import com.energetik.app.sntapplication.entity.Role;
import com.energetik.app.sntapplication.service.impl.RoleServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RoleServiceTest {

    @Autowired
    private RoleServiceImpl roleService;


    @Test
    @DisplayName("Поиск роли по ID")
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            value = "classpath:script/service/RoleServiceTest/before.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = "classpath:script/service/RoleServiceTest/after.sql")
    void findRoleByIdTest() {
        Optional<Role> optionalRole = roleService.findRoleById(1L);
        assertTrue(optionalRole.isPresent());
        assertEquals("USER", optionalRole.get().getRolename());
        optionalRole = roleService.findRoleById(3L);
        assertTrue(optionalRole.isEmpty());
    }


    @Test
    @DisplayName("Поиск роли по названию")
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            value = "classpath:script/service/RoleServiceTest/before.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = "classpath:script/service/RoleServiceTest/after.sql")
    void findRoleByRolenameTest() {
        Optional<Role> optionalRole = roleService.findRoleByRolename("ADMIN");
        assertTrue(optionalRole.isPresent());
        assertEquals(2L, optionalRole.get().getId());
        optionalRole = roleService.findRoleByRolename("abc");
        assertTrue(optionalRole.isEmpty());
    }


    @Test
    @DisplayName("Проверка на существование роли по ID")
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            value = "classpath:script/service/RoleServiceTest/before.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = "classpath:script/service/RoleServiceTest/after.sql")
    void existRoleByIdTest() {
        assertTrue(roleService.existRoleById(1L));
        assertFalse(roleService.existRoleById(3L));
    }

    @Test
    @DisplayName("Сохранение роли")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = "classpath:script/service/RoleServiceTest/after.sql")
    void saveRoleTest() {
        assertThrows(IllegalArgumentException.class,
                () -> roleService.saveRole(null));

        Role role = new Role();
        role.setRolename(null);
        assertThrows(IllegalArgumentException.class,
                () -> roleService.saveRole(role));

        Role role2 = new Role();
        role2.setRolename("testRole");
        roleService.saveRole(role2);
        Optional<Role> optionalRole = roleService.findRoleByRolename("testRole");
        assertTrue(optionalRole.isPresent());
        assertEquals("testRole", optionalRole.get().getRolename());

        assertThrows(IllegalArgumentException.class,
                () -> roleService.saveRole(role2));
    }


    @Test
    @DisplayName("Обновление роли по ID")
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            value = "classpath:script/service/RoleServiceTest/before.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = "classpath:script/service/RoleServiceTest/after.sql")
    void updateRoleTest() {
        assertThrows(IllegalArgumentException.class,
                () -> roleService.updateRole(1L, null));

        Role r = new Role();
        assertThrows(IllegalArgumentException.class,
                () -> roleService.updateRole(1L, r));

        assertThrows(IllegalArgumentException.class,
                () -> roleService.updateRole(-1L, r));

        assertThrows(IllegalArgumentException.class,
                () -> roleService.updateRole(null, r));

        Optional<Role> optionalRole = roleService.findRoleById(1L);
        assertTrue(optionalRole.isPresent());
        assertEquals("USER", optionalRole.get().getRolename());
        Role role = new Role();
        role.setId(optionalRole.get().getId());
        role.setRolename("abc");
        roleService.updateRole(1L, role);
        optionalRole = roleService.findRoleById(1L);
        assertTrue(optionalRole.isPresent());
        assertEquals("abc", optionalRole.get().getRolename());

        assertThrows(IllegalArgumentException.class,
                () -> roleService.updateRole(100L, r));
    }

    @Test
    @DisplayName("Удаление роли по ID")
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            value = "classpath:script/service/RoleServiceTest/before.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = "classpath:script/service/RoleServiceTest/after.sql")
    void deleteRoleByIdTest() {
        assertThrows(IllegalArgumentException.class,
                () -> roleService.deleteRoleById(null));
        assertThrows(IllegalArgumentException.class,
                () -> roleService.deleteRoleById(999L));

        assertTrue(roleService.existRoleById(1L));
        roleService.deleteRoleById(1L);
        assertFalse(roleService.existRoleById(1L));
    }
}
