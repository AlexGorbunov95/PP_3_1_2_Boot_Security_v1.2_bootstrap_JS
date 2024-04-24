package ru.kata.spring.boot_security.demo.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.Query;
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

    public User getUserByUsername(String name) {
        String hql = "FROM User u WHERE u.name = :username";
        Query query = entityManager.createQuery(hql);
        query.setParameter("username", name);
        return (User) query.getSingleResult();
    }

}
