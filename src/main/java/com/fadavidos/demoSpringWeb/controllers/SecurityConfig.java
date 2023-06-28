package com.fadavidos.demoSpringWeb.controllers;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    /*
    It was replaced by the next method. This method was using demo users.

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
        userDetailsManager.createUser(
                User.withDefaultPasswordEncoder() // It shouldn't be use in production
                        .username("user")
                        .password("password")
                        .roles("USER")
                        .build());
        userDetailsManager.createUser(
                User.withDefaultPasswordEncoder() // It shouldn't be use in production
                        .username("admin")
                        .password("password")
                        .roles("ADMIN")
                        .build());
        return userDetailsManager;
    }
     */

    interface GrantedAuthorityCnv extends Converter<String, GrantedAuthority> {}

    @Bean
    @ConfigurationPropertiesBinding
    GrantedAuthorityCnv converter() {
        return SimpleGrantedAuthority::new;
    }


    @Bean
    UserDetailsService userService(UserRepository repo){
        return username -> repo.findByUsername(username).asUser();
    }


    @Bean
    CommandLineRunner initUsers(UserManagementRepository repository) {
        return args -> {
            repository.save(new UserAccount("alice", "password", "ROLE_USER"));
            repository.save(new UserAccount("bob", "password", "ROLE_USER"));
            repository.save(new UserAccount("test1", "password", "ROLE_NOTHING"));
            repository.save(new UserAccount("test2", "password", "ROLE_USER"));
            repository.save(new UserAccount("test3", "password", "ROLE_ADMIN"));
            repository.save(new UserAccount("admin", "password", "ROLE_ADMIN"));
        };
    }

    //SecurityFilterChain is the bean type needed to define a Spring Security policy
    @Bean
    SecurityFilterChain configureSecurity(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers("/login").permitAll()
                .requestMatchers("/", "/search").authenticated()
                .requestMatchers(HttpMethod.GET, "/api/**").authenticated()
                .requestMatchers("/admin").hasRole("ADMIN")
                .requestMatchers(
                        HttpMethod.POST,
                        "/delete/**",
                        "/new-video",
                        "/universal-search",
                        "/multi-field-search"
                ).authenticated() //
                .anyRequest().denyAll()
                .and()
                .formLogin()
                .and()
                .httpBasic();
        return http.build();
    }

}
