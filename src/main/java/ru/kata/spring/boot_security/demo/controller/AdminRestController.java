package ru.kata.spring.boot_security.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.exception.NoSuchUserException;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin/api")
public class AdminRestController {
    private final UserService users;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public AdminRestController(UserService users, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.users = users;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(users.index(), HttpStatus.OK);
    }


    @GetMapping("/users/{id}")
    public ResponseEntity<User> show(@PathVariable int id) {
        User user = users.show(id);
        if (user == null) {
            throw new NoSuchUserException("There is no user with ID = " +
                    id + " in the Database");
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<User> newUser(@RequestBody User user) {
        List<Role> rolesList = new ArrayList<>();
        for (Role role : user.getRoles()) {
            rolesList.add(roleService.getByName("ROLE_" + role));
        }
        user.setRoles(rolesList);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        users.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable("id") int id) {
        List<Role> rolesList = new ArrayList<>();
        for (Role role : user.getRoles()) {
            rolesList.add(roleService.getById(role.getId()));
        }
        user.setRoles(rolesList);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        users.update(id, user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        User user = users.show(id);
        if (user == null) {
            throw new NoSuchUserException("There is no user with ID = " + id + " in the Database");
        }
        users.delete(id);
        return new ResponseEntity<>("User with ID = " + id + " was deleted", HttpStatus.OK);
    }
}
