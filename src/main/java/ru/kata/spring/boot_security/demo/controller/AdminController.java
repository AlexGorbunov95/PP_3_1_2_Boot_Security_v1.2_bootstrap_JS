package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.CustomUserDetailsService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Collections;

@Controller
@RequestMapping("/admin")
public class AdminController {


    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private UserService userService;


    //переопределение на страницу входа
    @GetMapping(value = "/")
    public String login() {

        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logoutPage() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            SecurityContextHolder.getContext().setAuthentication(null);
        }

        return "redirect:/login?logout";
    }

    // Все юзеры

    @GetMapping
    public String index(Model model, Authentication authentication) {
        String userName = authentication.getName();
        User user = (User) customUserDetailsService.loadUserByUsername(userName);
        model.addAttribute("addRoles", userService.listUsers());
        model.addAttribute("userInfo", user);
        model.addAttribute("users", userService.listUsers());
        model.addAttribute("newUser", new User());

        return "users";
    }

    //добавить юзера
    @PostMapping(value = "/create")
    public String create(@ModelAttribute("newUser") User user, @RequestParam(name = "role", required = true) String roleName) {
        if (roleName.equals("ADMIN")) {
            user.setRoles(Collections.singleton(new Role(2L, roleName)));
        } else {
            user.setRoles(Collections.singleton(new Role(1L, roleName)));
        }
        userService.add(user);
        return "redirect:/admin";
    }

    //изменить юзера

    @PostMapping("/edit/{id}")
    public String update(@PathVariable("id") Long id, @ModelAttribute User user, @RequestParam(name = "editrole", required = true) String roleName) {

        userService.update(id, user, roleName);
        return "redirect:/admin";
    }


    //удалить юзера
    @PostMapping(value = "/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }

}
