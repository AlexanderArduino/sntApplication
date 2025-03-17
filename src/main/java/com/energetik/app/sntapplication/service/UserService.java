package com.energetik.app.sntapplication.service;




import com.energetik.app.sntapplication.security.User;

import java.util.Optional;

public interface UserService {

    Optional<User> findUserById(Long id);

    Optional<User> findUserByUsername(String username);

    boolean existUserById(Long id);

    void saveUser(User user);

    void updateUser(User user);

    void deleteUserById(Long id);
}
