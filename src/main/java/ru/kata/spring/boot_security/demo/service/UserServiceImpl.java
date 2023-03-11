package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserDetailsService {

    private final UserDao userdao;

    @Autowired
    public UserServiceImpl(UserDao userdao) {
        this.userdao = userdao;
    }

    public List<User> index() {
        return userdao.findAll();
    }

    public User show(int id) {
        return userdao.findById(id).orElse(null);
    }

    @Transactional
    public void save(User user) {
        userdao.save(user);
    }

    @Transactional
    public void update(int id, User user) {
        user.setId(id);
        userdao.save(user);
    }

    @Transactional
    public void delete(int id) {
        userdao.deleteById(id);
    }

    public User getUserByUsername(String username) {

        Optional<User> user = userdao.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        return user.orElse(null);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userdao.findByUsername(username).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }
}
