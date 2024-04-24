package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.CustomUserDetailsService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


@Controller
@RequestMapping("/")
public class UserController {
@Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    // Все юзеры

    @GetMapping(value = "/admin")
    public String index(Model model) {
        model.addAttribute("users", userService.listUsers());
        return "users";
    }

    // добавить юзера
    @GetMapping(value = "/new")
    public String newUser(Model model) {
        model.addAttribute("newUser", new User());
        return "new";
    }

    //добавить юзера
    @PostMapping(value = "/create")
    public String create(@ModelAttribute("newUser") User user) {
        userService.add(user);

        return "redirect:/admin";
    }

    // изменить юзера
    @GetMapping(value = "/editUser/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        User user = userService.showUser(id);
        model.addAttribute("editUser", user);
        return "edit";
    }

    //изменить юзера
    @PostMapping(value = "/update")
    public String update(@ModelAttribute("editUser") User user, Long id) {
        userService.update(id, user);
        return "redirect:/admin";
    }

    //удалить юзера
    @GetMapping(value = "/deleteUser/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }

    //Данные авторизованного юзера
    @GetMapping(value = "/user")
    public String getUser(Model model, Authentication authentication) {
        String userName = authentication.getName();
        User user = (User) userDetailsService.loadUserByUsername(userName);

        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/logout")
    public String logoutPage() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            SecurityContextHolder.getContext().setAuthentication(null);
        }

        return "redirect:/login?logout";
    }
}
