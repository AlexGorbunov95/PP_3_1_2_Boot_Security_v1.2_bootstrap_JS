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
@RequestMapping("/user")
public class UserController {
    @Autowired
    private CustomUserDetailsService userDetailsService;


    //переопределение на страницу входа
    @GetMapping(value = "/")
    public String login() {

        return "redirect:/login";
    }

    //Данные авторизованного юзера
    @GetMapping()
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
