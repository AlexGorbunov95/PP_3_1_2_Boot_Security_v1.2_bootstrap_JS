package ru.kata.spring.boot_security.demo.dao;



import antlr.BaseAST;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


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

//        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));

        //        Set<Role> roles = new HashSet<>();
//        roles.add(new Role(1L, "ROLE_USER"));
//        roles.add(new Role(2L, "ROLE_ADMIN"));
//        user.setRoles(roles);
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
    public void update(Long id, User updateUser) {
        User udateUserId = showUser(id);
        udateUserId.setName(updateUser.getName());
        udateUserId.setLastName(updateUser.getLastName());
        udateUserId.setAge(updateUser.getAge());

    }

    @Transactional
    @Override
    public void delete(Long id) {
        entityManager.remove(entityManager.find(User.class, id));
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = entityManager.find(User.class,username);
        return new org.springframework.security.core.userdetails.User(
                user.getName(),
                user.getPassword(),
                user.getRoles()
        );
    }
    public User getUserByUsername(String name) {
        String hql = "FROM User u WHERE u.name = :username";
        Query query = entityManager.createQuery(hql);
        query.setParameter("username", name);
        return (User) query.getSingleResult();
    }

}
