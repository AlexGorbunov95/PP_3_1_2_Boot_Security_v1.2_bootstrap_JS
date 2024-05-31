package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.CustomUserDetailsService;

import java.security.Principal;

@RestController
@RequestMapping({"/api/user"})
public class UserRestController {

    @Autowired
    private CustomUserDetailsService userDetailsService;


    @GetMapping()
    public ResponseEntity<User> showUser(Principal principal) {
        return ResponseEntity.ok((User) userDetailsService.loadUserByUsername(principal.getName()));
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
