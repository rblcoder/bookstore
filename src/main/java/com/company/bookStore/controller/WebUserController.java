package com.company.bookStore.controller;

import com.company.bookStore.exception.UserNotFoundException;
import com.company.bookStore.model.User;
import com.company.bookStore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class WebUserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public String listAllUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "user/users";
    }

    @GetMapping("/users/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("pageTitle", "Create New User");
        model.addAttribute("roles", userService.getAllRoles());
        return "user/user_form";
    }

    @GetMapping("/users/edit/{id}")
    public String editUser(Model model, @PathVariable Long id) {
        User user = userService.getUserById(id)
                .orElseThrow(()->new UserNotFoundException());
        model.addAttribute("user", user);
        model.addAttribute("pageTitle", "Edit User");
        model.addAttribute("roles", userService.getAllRoles());
        return "user/user_form";
    }

}
