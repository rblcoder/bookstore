package com.company.bookStore.service;

import com.company.bookStore.model.Role;
import com.company.bookStore.model.User;
import com.company.bookStore.repository.RoleRepository;
import com.company.bookStore.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private Set<Role> roles;

    @BeforeAll
    void setUp() {
        roles = new HashSet<>();
        Role roleAdmin = Role.builder().id(1L)
                .name("admin")
                .description("Administrator").build();
        roles.add(roleAdmin);
    }

    @Test
    void shouldGetAllUsers() {
        User user = User.builder().id(1L)
                .email("emily@email.com")
                .firstName("Emily")
                .lastName("Chacko")
                .enabled(true)
                .password(passwordEncoder.encode("password"))
                .roles(roles).build();

        List<User> userList = new ArrayList<>();
        userList.add(user);

        when(userRepository.findAll()).thenReturn(userList);

        Assertions.assertEquals(userList, userService.getAllUsers());

    }

    @Test
    void shouldGetAllRoles() {
        List<Role> roleList = roles.stream().collect(Collectors.toList());

        when(roleRepository.findAll()).thenReturn(roleList);
        Assertions.assertEquals(roleList, userService.getAllRoles());
    }

    @Test
    void shouldGetUserById(){
        User user = User.builder().id(1L)
                .email("emily@email.com")
                .firstName("Emily")
                .lastName("Chacko")
                .enabled(true)
                .password(passwordEncoder.encode("password"))
                .roles(roles).build();

        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));

        Assertions.assertEquals(Optional.ofNullable(user), userService.getUserById(1L));
    }

    @Test
    void shouldSaveUser() {

        User userSaved = User.builder().id(1L)
                .email("emily@email.com")
                .firstName("Emily")
                .lastName("Chacko")
                .enabled(true)
                .password(passwordEncoder.encode("password"))
                .roles(roles).build();

        User user = User.builder().id(1L)
                .email("emily@email.com")
                .firstName("Emily")
                .lastName("Chacko")
                .enabled(true)
                .password("password")
                .roles(roles).build();

        when(userRepository.save(userSaved)).thenReturn(userSaved);

        Assertions.assertEquals(userSaved, userService.save(user));

    }
}
