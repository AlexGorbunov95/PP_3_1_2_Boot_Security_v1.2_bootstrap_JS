package ru.kata.spring.boot_security.demo.dao;


import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Repository
public class UserDaoimp implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    private final RoleService roleService;

    public UserDaoimp(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public void add(User user) {
        entityManager.persist(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    public User showUser(long id) {
        return entityManager.find(User.class, id);

    }

    @Override
    public void editUser(Long id, User updateUser, List<Long> roleIds) {
        User updateUserId = showUser(id);
        Set<Role> roles = new HashSet<>();
        for (Long roleId : roleIds) {
            roles.add(roleService.showRole(roleId));
        }
        updateUserId.setRoles(roles);
        updateUserId.setName(updateUser.getName());
        updateUserId.setLastName(updateUser.getLastName());
        updateUserId.setAge(updateUser.getAge());
        updateUserId.setPassword(updateUser.getPassword());
        updateUserId.setEMail(updateUser.getEMail());
    }

    @Override
    public void delete(Long id) {
        entityManager.remove(entityManager.find(User.class, id));
    }

    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    public User getUserByUsername(String eMail) {
        String hql = "FROM User u WHERE u.eMail = :username";
        Query query = entityManager.createQuery(hql);
        query.setParameter("username", eMail);
        return (User) query.getSingleResult();
    }
}
