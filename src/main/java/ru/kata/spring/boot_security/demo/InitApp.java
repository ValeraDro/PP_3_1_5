package ru.kata.spring.boot_security.demo;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.dao.RoleRepository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Component
public class InitApp {

    private final UserService users;
    private final RoleRepository roleService;

    public InitApp(UserService users, RoleRepository roleService) {

        this.users = users;
        this.roleService = roleService;

        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setAge(5);
        users.save(admin);

        List<Role> rolesAdmin = new ArrayList<>();
        Role roleAdmin = roleService.findById(1).orElse(null);
        Role roleUser = roleService.findById(2).orElse(null);
        rolesAdmin.add(roleAdmin);
        rolesAdmin.add(roleUser);
        admin.setRoles(rolesAdmin);
        users.update(admin.getId(), admin);


        User user = new User();
        user.setUsername("user");
        user.setPassword("user");
        user.setAge(55);
        users.save(user);

        List<Role> rolesUser = new ArrayList<>();
        rolesUser.add(roleUser);
        user.setRoles(rolesUser);
        users.update(user.getId(), user);

    }
}
