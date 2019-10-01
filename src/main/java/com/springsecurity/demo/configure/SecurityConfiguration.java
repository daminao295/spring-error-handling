package com.springsecurity.demo.configure;

import com.springsecurity.demo.model.User;
import com.springsecurity.demo.repository.UserRepository;
import com.springsecurity.demo.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private UserDetailsServiceImpl userService;

    private UserRepository userRepository;

    @Autowired
    public SecurityConfiguration(UserDetailsServiceImpl userService,UserRepository userRepository) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/moderator").hasRole("MODERATOR")
                .antMatchers("/user").permitAll()
                .and()
                .formLogin().permitAll()
                .and()
                .logout().permitAll();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @EventListener(ApplicationReadyEvent.class)
    private void loadusers(){
        User admin = new User ("admin", passwordEncoder().encode("admin"), "ROLE_ADMIN");
        User moderator = new User ("moderator", passwordEncoder().encode("moderator"), "ROLE_MODERATOR");
        User user = new User ("user", passwordEncoder().encode("user"), "ROLE_USER");

        userRepository.save(admin);
        userRepository.save(moderator);
        userRepository.save(user);
    }
}
