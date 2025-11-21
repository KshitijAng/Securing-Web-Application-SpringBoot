# Securing a Web Application with Spring Boot, Spring MVC & Spring Security
 
This project implements a simple web application that uses **Spring MVC** for the web layer, **Thymeleaf** for view templates, and **Spring Security** to protect certain endpoints. The application includes a public home page and a secured greeting page. Users must authenticate to view the greeting.

## What You Will Build  
- A web application built with Spring Boot and Spring MVC.  
- A home page accessible to anyone.  
- A “Hello world” page (`/hello`) that is secured; only authenticated users may access it.  
- A custom login form at `/login`.  
- In-memory user details with a single user:  
  - username: `user`  
  - password: `password` (encoded)  
  - role: `USER`

## Technologies & Dependencies  
### Spring MVC  
The application uses Spring MVC as the web framework. View controllers map specific paths (such as `/`, `/home`, `/hello`, `/login`) to Thymeleaf templates without requiring explicit controller methods.

### Thymeleaf  
Thymeleaf is used as the server-side template engine.  
- It renders HTML in `src/main/resources/templates/*.html`.  
- It provides support for Spring MVC, allowing expressions like `th:href="@{/hello}"`.  
- With the extra dependency `thymeleaf-extras-springsecurity6`, Thymeleaf also integrates with Spring Security features (e.g., retrieving the authenticated user name, controlling display based on authentication state).

### Spring Security  
By adding `spring-boot-starter-security` to the classpath, Spring Boot auto-configures basic web security (HTTP Basic by default) when no custom configuration is provided.  
In this implementation a custom `WebSecurityConfig` class is created to:  
- Define a `SecurityFilterChain` bean that configures which URLs are permitted without authentication and which require it.  
- Specify a custom login page (`/login`).  
- Define a `PasswordEncoder` bean (using `BCryptPasswordEncoder`).  
- Define a `UserDetailsService` bean (in-memory) with the test user.

## Security Configuration

The `WebSecurityConfig` class is annotated with `@EnableWebSecurity` and defines the core security setup for the application.

### SecurityFilterChain Bean
The security filter chain configures how HTTP requests are authorized and authenticated:

- **Public Access:**  
  `/` and `/home` are accessible to all users without authentication.

- **Protected Routes:**  
  Any other request requires the user to be authenticated.

- **Form-Based Login:**  
  Custom login page configured at `/login`.

- **Logout Support:**  
  Logout is permitted for all users.


**PasswordEncoder** Bean -> A `PasswordEncoder` bean (using `BCryptPasswordEncoder`) is defined to securely hash user passwords before storage and comparison.


**UserDetailsService** Bean -> The application configures an in-memory user store using `InMemoryUserDetailsManager`.

```java
UserDetails user = User.builder()
    .username("user")
    .password(passwordEncoder.encode("password"))
    .roles("USER")
    .build();

return new InMemoryUserDetailsManager(user);
```

## Summary

This application demonstrates how to:
- Use Spring MVC with Thymeleaf for server-side view rendering.
- Add form-based authentication using Spring Security.
- Map views using addViewControllers() instead of writing explicit controllers for simple static pages.
- Secure application endpoints and customise login/logout behaviour.

Integrate Thymeleaf with Spring Security to:
- Display authenticated user information.
- Manage logout actions.

