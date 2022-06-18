package com.company.bookStore.controller;

import com.company.bookStore.model.Genre;
import com.company.bookStore.model.User;
import com.company.bookStore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class WebUserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public String listAllUsers(Model model){
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "user/users";
    }

    @GetMapping("/users/new")
    public String newGenre(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("pageTitle", "Create New User");
        model.addAttribute("allRoles", userService.getAllRoles());
        return "user/user_form";
    }

}
