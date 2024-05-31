package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.CustomUserDetailsService;

@Controller
@RequestMapping("/admin")
public class AdminController {


    @Autowired
    private CustomUserDetailsService customUserDetailsService;


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

    //    // Все юзеры
//
    @GetMapping
    public String index(Model model, Authentication authentication) {
        String userName = authentication.getName();
        User user = (User) customUserDetailsService.loadUserByUsername(userName);
        model.addAttribute("user", user);


        return "admin";
    }


}
