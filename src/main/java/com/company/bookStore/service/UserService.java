package com.company.bookStore.service;

import com.company.bookStore.model.Role;
import com.company.bookStore.model.User;
import com.company.bookStore.repository.RoleRepository;
import com.company.bookStore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public  List<Role> getAllRoles(){
        return roleRepository.findAll();
    }
}
