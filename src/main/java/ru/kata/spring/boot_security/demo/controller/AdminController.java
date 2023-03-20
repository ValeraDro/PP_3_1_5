package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {
    private final UserService users;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService users, RoleService roleService) {
        this.users = users;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String index(Model model) {
        model.addAttribute("users", users.index());
        return "admin/list";
    }

    @GetMapping("/admin/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", users.show(id));
        return "admin/show";
    }

    @GetMapping("/admin/new")
    public String newUser(ModelMap model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.findAll());
        return "admin/new";
    }

    @PostMapping("/admin")
    public String create(@ModelAttribute("user") User user/*,
                         @RequestParam(value = "roles") String[] roles*/) {
//        List<Role> rolesList = new ArrayList<>();
//        for (String role : roles) {
//            rolesList.add(roleService.getByName("ROLE_"+role));
//        }
//        user.setRoles(rolesList);
        users.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("user", users.show(id));
        return "admin/edit";
    }

    @PatchMapping("/admin/{id}")
    public String update(@ModelAttribute("user") User user,
                         @PathVariable("id") int id/*, @RequestParam(value = "roles") String[] roles*/) {
//        List<Role> rolesList = new ArrayList<>();
//        for (String role : roles) {
//            rolesList.add(roleService.getByName("ROLE_"+role));
//        }
//        user.setRoles(rolesList);
        users.update(id, user);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/{id}")
    public String delete(@PathVariable("id") int id) {
        users.delete(id);
        return "redirect:/admin";
    }
}
