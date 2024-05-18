package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;


@Service("userService")
public class UserServiceImp implements UserService {

    @Autowired
    private UserDao userDao;

    @Transactional
    @Override
    public void add(User user) {
        userDao.add(user);
    }

    @Transactional
    @Override
    public List<User> listUsers() {
        return userDao.listUsers();
    }


    @Transactional(readOnly = true)
    @Override
    public User showUser(long id) {
        return userDao.showUser(id);
    }

    @Transactional
    @Override
    public void update(Long id, User user, List<Long> roleIds) {
        userDao.update(id, user, roleIds);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        userDao.delete(id);
    }

    @Transactional
    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }


}
