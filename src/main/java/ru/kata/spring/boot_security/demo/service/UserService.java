package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    List<User> index();

    User show(int id);

    void save(User user);

    void update(int id, User user);

    void delete(int id);

    List<User> findByUsername(String username);

    User findByEmail(String email);
}
