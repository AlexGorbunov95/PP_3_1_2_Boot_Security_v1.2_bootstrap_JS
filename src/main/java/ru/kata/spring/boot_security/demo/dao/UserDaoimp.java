package ru.kata.spring.boot_security.demo.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Collections;
import java.util.List;


@Repository
public class UserDaoimp implements UserDao {

    private final EntityManager entityManager;

    @Autowired
    public UserDaoimp(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Transactional
    @Override
    public void add(User user) {
        entityManager.persist(user);
    }

    @Transactional
    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Transactional(readOnly = true)
    @Override
    public User showUser(long id) {
        return entityManager.find(User.class, id);

    }

    @Transactional
    @Override
    public void update(Long id, User updateUser, String roleName) {
        User updateUserId = showUser(id);
        if (roleName.equals("ADMIN")){
            updateUserId.setRoles(Collections.singleton(new Role(2L ,roleName)));
        }
        else {
            updateUserId.setRoles(Collections.singleton(new Role(1L ,roleName)));
        }
        updateUserId.setName(updateUser.getName());
        updateUserId.setLastName(updateUser.getLastName());
        updateUserId.setAge(updateUser.getAge());
        updateUserId.setPassword(updateUser.getPassword());
        updateUserId.setEMail(updateUser.getEMail());
    }

    @Transactional
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

    @Transactional
    @Override
    @SuppressWarnings("unchecked")
    public List<Role> listRole() {
        return entityManager.createQuery("SELECT r FROM Role r", Role.class).getResultList();
    }

    @Transactional(readOnly = true)
    @Override
    public Role showRole(long id) {
        return entityManager.find(Role.class, id);

    }
}
