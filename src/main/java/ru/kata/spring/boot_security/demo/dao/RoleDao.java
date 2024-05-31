package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleDao {

    List<Role> getAllRoles();
    Set<Role> rolesSet();

    Role showRole(long id);

    void addRole(Role role);

    Role showRoleName(String roleName);
}
