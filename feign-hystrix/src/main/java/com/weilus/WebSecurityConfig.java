package com.weilus;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * @program springCloud
 * @date 2019-05-23 14:27
 **/
@EnableWebSecurity
public class WebSecurityConfig{

    @Bean
    public UserDetailsService userDetailsService() throws Exception {
        User.UserBuilder users = User.withDefaultPasswordEncoder();
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(users.username("abc").password("123").roles("USER").build());
        manager.createUser(users.username("dfg").password("321").roles("USER","ADMIN").build());
        return manager;
    }

    @Configuration
    @Order(1)
    public static class TestApiConfigurer extends WebSecurityConfigurerAdapter {
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable();
            http.antMatcher("/api/**").authorizeRequests().anyRequest().hasRole("USER")
                    .and()
                    .httpBasic();
        }
    }
}
