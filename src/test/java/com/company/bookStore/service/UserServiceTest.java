package com.company.bookStore.service;

import com.company.bookStore.model.Genre;
import com.company.bookStore.model.Role;
import com.company.bookStore.model.User;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

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
}
