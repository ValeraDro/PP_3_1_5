package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

@Primary
@Service
public class UserServiceImpl extends UserSecurity implements UserService {

    public UserServiceImpl(UserDao userdao) {
        super(userdao);
    }

    public List<User> index() {
        return userdao.index();
    }

    public User show(int id) {
        return userdao.show(id);
    }

    @Transactional
    public void save(User user) {
        userdao.save(user);
    }

    @Transactional
    public void update(int id, User user) {
        userdao.update(id, user);
    }

    @Transactional
    public void delete(int id) {
        userdao.delete(id);
    }

    public List<User> findByUsername(String username) {
        return userdao.findByUsername(username);
    }

    public User findByEmail(String email) {
        return userdao.findByEmail(email);
    }
}
