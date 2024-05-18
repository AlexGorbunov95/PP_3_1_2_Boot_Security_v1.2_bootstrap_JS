package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.Set;

public interface RoleService {

    Set<Role> rolesSet();

    Role showRole(long id);

    void addRole(Role role);

    Role showRoleName(String roleName);
}
