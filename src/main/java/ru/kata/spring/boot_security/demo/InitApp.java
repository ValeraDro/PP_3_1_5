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

        User admin = users.findByEmail("admin@mail.ru");
        if (admin == null) {
            admin = new User();
            admin.setFirstName("admin");
            admin.setEmail("admin@mail.ru");
            admin.setPassword("$2a$10$L5CenfUUbHtGR2yuqC6nWeXK2kI/xwcTH/HSXY.bfR3Ewukm3UC5K"); //admin
            admin.setAge(5);
            users.save(admin);
            admin.setRoles(rolesAdmin);
            users.update(admin.getId(), admin);
        }

        User user = users.findByEmail("user@mail.ru");
        if (user == null) {
            user = new User();
            user.setFirstName("user");
            user.setEmail("user@mail.ru");
            user.setPassword("$2a$10$8eR5O/xsSfpjvlgesKOP/.iUiWVpv3IP18HIXbIsep9/S9ykMC/MG"); //user
            user.setAge(55);
            users.save(user);
            user.setRoles(rolesUser);
            users.update(user.getId(), user);
        }
    }
}
