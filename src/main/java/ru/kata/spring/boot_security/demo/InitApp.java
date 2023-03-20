package ru.kata.spring.boot_security.demo;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Component
public class InitApp {

    private final UserService users;
    private final RoleService roleService;

    public InitApp(UserService users, RoleService roleService) {

        this.users = users;
        this.roleService = roleService;

        Role roleAdmin = roleService.getByName("ROLE_ADMIN");
        Role roleUser = roleService.getByName("ROLE_USER");

        List<Role> rolesAdmin = new ArrayList<>();
        rolesAdmin.add(roleAdmin);
        rolesAdmin.add(roleUser);

        List<Role> rolesUser = new ArrayList<>();
        rolesUser.add(roleUser);

        List<User> usersList = users.findByUsername("admin");
        if (usersList.isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("admin");
            admin.setAge(5);
            users.save(admin);
            admin.setRoles(rolesAdmin);
            users.update(admin.getId(), admin);
        }

        usersList = users.findByUsername("user");
        if (usersList.isEmpty()) {
            User user = new User();
            user.setUsername("user");
            user.setPassword("user");
            user.setAge(55);
            users.save(user);
            user.setRoles(rolesUser);
            users.update(user.getId(), user);
        }
    }
}
