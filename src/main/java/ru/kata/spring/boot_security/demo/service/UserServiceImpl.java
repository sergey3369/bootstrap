package ru.kata.spring.boot_security.demo.service;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.security.MyUserPrincipal;

import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserDao dao;

    public UserServiceImpl(UserDao dao) {
        this.dao = dao;
    }

    @Transactional
    @Override
    public List<User> getUsers() {
        return dao.getUsers();
    }

    @Transactional
    @Override
    public void saveUser(User user) {
        dao.saveUser(user);
    }

    @Transactional
    @Override
    public User getUser(int id) {
        return dao.getUser(id);
    }

    @Transactional
    @Override
    public void deleteUser(Integer id) {
        dao.deleteUser(id);
    }

    @Transactional
    @Override
    public void update(User user) { dao.update(user); }

    @Transactional
    @Override
    public User findByUsername(String username) {
        return dao.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = dao.findByUsername(username);
        return new MyUserPrincipal(user);
    }
}
