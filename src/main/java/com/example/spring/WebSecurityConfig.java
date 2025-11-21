package com.example.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
// The WebSecurityConfig class is annotated with @EnableWebSecurity to enable Spring Security’s web security support and provide the Spring MVC integration.
@EnableWebSecurity
public class WebSecurityConfig {


    // 1 The SecurityFilterChain bean defines which URL paths should be secured and which should not.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/home").permitAll() // the / and /home paths are configured to not require any authentication. All other paths must be authenticated.
                        .anyRequest().authenticated()
                )

                // use your own /login page (the one you mapped in MvcConfig)
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout((logout) -> logout.permitAll()); // everyone can log out.

        return http.build(); // builds and returns the SecurityFilterChain that Spring Security uses.
    }

    // 2 Declares a PasswordEncoder bean.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // BCryptPasswordEncoder is the standard for hashing passwords.
    }

    // 3 The UserDetailsService bean sets up an in-memory user store with a single user.
    @Bean
    public UserDetailsService userDetailsService() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        UserDetails user = User.builder() // Create an in-memory user
                        .username("user") // username: user
                        .password(encoder.encode("password")) // password: password (stored as BCrypt hash)
                        .roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(user); // Register it in an InMemoryUserDetailsManager so Spring Security can authenticate with it.
    }
}
