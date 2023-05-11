package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
public class AdminController {
    private final UserService users;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public AdminController(UserService users, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.users = users;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/admin")
    public String index(Model model, Principal principal) {
        User authenticatedUser = users.findByEmail(principal.getName());
        model.addAttribute("authenticatedUser", authenticatedUser);
        model.addAttribute("authenticatedUserRoles", authenticatedUser.getRoles());
        model.addAttribute("newUser", new User());
        model.addAttribute("roles", roleService.findAll());
        return "admin/index";
    }
}
