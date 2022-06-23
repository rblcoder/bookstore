package com.company.bookStore.config;

import com.company.bookStore.model.Book;
import com.company.bookStore.model.Genre;
import com.company.bookStore.model.Role;
import com.company.bookStore.model.User;
import com.company.bookStore.repository.BookRepository;
import com.company.bookStore.repository.GenreRepository;
import com.company.bookStore.repository.RoleRepository;
import com.company.bookStore.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;


@Configuration
public class PreLoadData {
    Logger logger = LoggerFactory.getLogger(PreLoadData.class);

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public @PostConstruct
    void init() {

        Genre genreNonFiction = new Genre(1L, "Non fiction");

        logger.info("Preloading " + genreRepository.save(genreNonFiction));

        Genre genreNonFictionSelfHelp = new Genre(2L, "Self-Help");
        genreNonFictionSelfHelp.setParent(genreNonFiction);

        logger.info("Preloading " + genreRepository.save(genreNonFictionSelfHelp));


        Book bookPeace = Book.builder()
                .id(1L).title("Peace")
                .publishedYear(2002L)
                .genre(genreNonFictionSelfHelp).build();
        logger.info("Preloading " + bookRepository.save(bookPeace));

        Genre genreComputer = new Genre(3L, "Computer Technology");
        genreComputer.setParent(genreNonFiction);

        logger.info("Preloading " + genreRepository.save(genreComputer));

        Genre genreJava = new Genre(4L, "Java");
        genreJava.setParent(genreComputer);

        logger.info("Preloading " + genreRepository.save(genreJava));


        Book bookJavaLearn = Book.builder()
                .id(2L).title("Learn Java 8")
                .publishedYear(2020L)
                .genre(genreJava).build();

        logger.info("Preloading " + bookRepository.save(bookJavaLearn));

        Role roleAdmin = Role.builder().id(1L)
                .name("Admin").description("Administration").build();

        logger.info("Preloading " + roleRepository.save(roleAdmin));

        Set<Role> roleSet = new HashSet<>();
        roleSet.add(roleAdmin);

        User userAdmin = User.builder().id(1L)
                .email("jack@email.com")
                .firstName("Jack")
                .lastName("Chacko")
                .password(passwordEncoder.encode("admin"))
                .roles(roleSet)
                .enabled(true).build();

        logger.info("Preloading " + userRepository.save(userAdmin));

        //

        Role roleUser = Role.builder().id(2L)
                .name("User").description("User").build();

        logger.info("Preloading " + roleRepository.save(roleUser));

        Set<Role> roleSetUser = new HashSet<>();
        roleSetUser.add(roleUser);

        User user = User.builder().id(2L)
                .email("sunita@email.com")
                .firstName("Sunita")
                .lastName("Pais")
                .password(passwordEncoder.encode("user"))
                .roles(roleSetUser)
                .enabled(true).build();

        logger.info("Preloading " + userRepository.save(user));

    }

}
