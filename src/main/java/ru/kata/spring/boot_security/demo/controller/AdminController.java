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
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {


    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

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
        model.addAttribute("setRoles", roleService.rolesSet());

        return "users";
    }

    //добавить юзера
    @PostMapping(value = "/create")
    public String create(@ModelAttribute("newUser") User user,
                         @RequestParam(name = "roles", required = true) List<Long> roleIds) {

        Set<Role> roles = new HashSet<>();
        for (Long roleId : roleIds) {
            roles.add(roleService.showRole(roleId));
        }
        user.setRoles(roles);
        userService.add(user);
        return "redirect:/admin";
    }

    //изменить юзера

    @PutMapping("/edit/{id}")
    public String update(@PathVariable("id") Long id, @ModelAttribute User user, @RequestParam(name = "roles", required = true) List<Long> roleIds) {
        userService.update(id, user, roleIds);
        return "redirect:/admin";
    }


    //удалить юзера
    @DeleteMapping(value = "/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }

}
