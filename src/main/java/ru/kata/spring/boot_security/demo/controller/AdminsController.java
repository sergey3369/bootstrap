package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminsController {
    final private PasswordEncoder passwordEncoder;
    final private RoleService roleService;
    final private UserService userService;

    public AdminsController(PasswordEncoder passwordEncoder, UserService userService, RoleService roleService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("")
    public String getUsers(Authentication authentication, Model model) {
        List<User> users = userService.getUsers();
        List<Role> roles = roleService.getRole();
        model.addAttribute("allRoles", roles);
        model.addAttribute("firstrole", authentication.getAuthorities().stream().map(Object::toString).collect(Collectors.joining(" ")));
        model.addAttribute("firstuser", authentication.getName());
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/{id}/edit")
    public String getEditForm(@PathVariable Long id, Model model) {
        User userEdit = userService.getUser(Math.toIntExact(id));
        List<Role> roles = roleService.getRole();
        model.addAttribute("allRoles", roles);
        model.addAttribute("user", userEdit);
        return "update";
    }

    @PostMapping("/adduser")
    public String addUser(@Validated(User.class) @ModelAttribute("user") User user,
                          @RequestParam("authorities") List<String> values,
                          BindingResult result) {
        if(result.hasErrors()) {
            return "error";
        }
        Set<Role> roleSet = userService.getSetOfRoles(values);
        user.setRoles(roleSet);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PostMapping("update")
    public String updateUser(@Validated(User.class) @ModelAttribute("user") User user,
                             @RequestParam("authorities") List<String> values,
                             BindingResult result) {
        if(result.hasErrors()) {
            return "error";
        }
        userService.getSetOfRoles(values);
        if (!user.getPassword().equals(userService
                .getUser(Math.toIntExact(user.getId()))
                .getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userService.update(user);
        return "redirect:/admin";
    }

    @GetMapping("/new")
    public String newUserForm(Model model, Authentication authentication) {
        model.addAttribute(new User());
        User user = userService.findByUsername(authentication.getName());
        List<Role> roles = roleService.getRole();
        model.addAttribute("allRoles", roles);
        model.addAttribute("firstrole", authentication.getAuthorities().stream().map(Object::toString).collect(Collectors.joining(" ")));
        model.addAttribute("firstuser", authentication.getName());
        return "create";
    }

    @GetMapping("/{id}/delete")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(Math.toIntExact(id));
        return "redirect:/admin";
    }

}
