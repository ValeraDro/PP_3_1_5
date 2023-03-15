package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;


public class UserSecurity implements UserDetailsService {

    final UserDao userdao;

    public UserSecurity(UserDao userdao) {
        this.userdao = userdao;
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        List<User> users = userdao.findByUsername(username);
        if (users.isEmpty()) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }

        return new org.springframework.security.core.userdetails.User(users.get(0).getUsername(), users.get(0).getPassword(), users.get(0).getAuthorities());
    }
}
