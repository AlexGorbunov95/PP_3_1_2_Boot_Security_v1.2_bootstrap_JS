package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;


@Service("userService")
public class UserServiceImp implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public void add(User user) {
        userDao.add(user);
    }

    @Override
    public List<User> listUsers() {
        return userDao.listUsers();
    }

    @Override
    public User showUser(long id) {
        return userDao.showUser(id);
    }

    @Override
    public void update(Long id, User user, String roleName) {
        userDao.update(id, user,roleName);
    }

    @Override
    public void delete(Long id) {
        userDao.delete(id);
    }

    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Override
    public List<Role> listRole() {
        return userDao.listRole();
    }

    @Override
    public Role showRole(long id) {
        return userDao.showRole(id);
    }


}
