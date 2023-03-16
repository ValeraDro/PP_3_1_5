package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
public class UserController {

    private final UserService users;

    @Autowired
    public UserController(UserService users) {
        this.users = users;
    }

    @GetMapping(value = "/")
    public String printWelcome() {
        return "redirect:/user";
    }

    @GetMapping("/user")
    public String showUser(ModelMap model, Principal principal) {
        List<User> user = users.findByUsername(principal.getName());
        System.out.println(user.get(0));
        model.addAttribute("user", user.get(0));
        return "admin/show";
    }
}

