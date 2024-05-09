package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    void add(User user);

    List<User> listUsers();

    User showUser(long id);

    void update(Long id, User user, String roleName);

    void delete(Long id);

    void updateUser(User user);

    List<Role> listRole();

    Role showRole(long id);

}
