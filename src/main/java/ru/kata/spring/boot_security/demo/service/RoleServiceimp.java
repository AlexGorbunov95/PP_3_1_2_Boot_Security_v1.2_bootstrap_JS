package ru.kata.spring.boot_security.demo.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;
import java.util.Set;

@Service
public class RoleServiceimp implements RoleService {

    final RoleDao roleDao;

    public RoleServiceimp(RoleDao roleDao) {
        this.roleDao = roleDao;
    }


    @Override
    public List<Role> getAllRoles() {
        return roleDao.getAllRoles();
    }

    @Override
    public Set<Role> rolesSet() {
        return roleDao.rolesSet();
    }

    @Override
    public Role showRole(long id) {
        return roleDao.showRole(id);
    }

    @Transactional
    @Override
    public void addRole(Role role) {
        roleDao.addRole(role);
    }

    @Override
    public Role showRoleName(String roleName) {
        return roleDao.showRoleName(roleName);
    }
}
