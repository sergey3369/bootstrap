package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class UserController {
    private UserService userService;
    private RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }

    @GetMapping("/user")
    public String userPage(Model model, Authentication authentication) {
        model.addAttribute("firstrole", authentication.getAuthorities().stream().map(Object::toString).collect(Collectors.joining(" ")));
        model.addAttribute("firstuser", authentication.getName());
        User user = userService.findUserByName(authentication.getName());
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/admin")
    public String getUsers(Authentication authentication, Model model) {
        List<User> users = userService.listUsers();
        List<Role> roles = roleService.getRolesList();
        model.addAttribute("allRoles", roles);
        model.addAttribute("firstrole", authentication.getAuthorities().stream().map(Object::toString).collect(Collectors.joining(" ")));
        model.addAttribute("firstuser", authentication.getName());
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/new")
    public String newUserForm(Model model, Authentication authentication) {
        model.addAttribute(new User());
        List<Role> roles = roleService.getRolesList();
        model.addAttribute("allRoles", roles);
        model.addAttribute("firstrole", authentication.getAuthorities().stream().map(Object::toString).collect(Collectors.joining(" ")));
        model.addAttribute("firstuser", authentication.getName());
        return "create";
    }
}