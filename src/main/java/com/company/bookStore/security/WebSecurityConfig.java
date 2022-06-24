package com.company.bookStore.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;

@Configuration
public class WebSecurityConfig {
    @Bean
    public PasswordEncoder PasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public BookShopUserDetailsService userDetailsService() {
        return new BookShopUserDetailsService();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(PasswordEncoder());

        return authProvider;
    }

    @Bean
    protected DefaultSecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/genres/**").authenticated()
                .antMatchers("/users/**").authenticated()
                .antMatchers("/", "/home").authenticated()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/api/v1/books/**").authenticated()
                .antMatchers("/images/**").permitAll()
                .antMatchers("/login/**").permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/home")
                .failureUrl("/login?error")
                .usernameParameter("email")
                .and()
                .logout().logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true);

        http.headers().frameOptions().sameOrigin();


        return http.build();

    }

}
