package ru.kata.spring.boot_security.demo.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AdminsController {
    private UserService userService;

    public AdminsController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/allUsers")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.listUsers());
    }

    @PostMapping("/adduser")
    public User addUser(@RequestBody User user) {
        userService.saveUser(user);
        return user;
    }

    @PutMapping("/edit/{id}")
    public User updateUser(@RequestBody User user) {
        userService.updateUser(user);
        return user;
    }

    @DeleteMapping("/{id}/delete")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

}
