package com.weilus;

import de.codecentric.boot.admin.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.turbine.EnableTurbine;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author 1
 * @program springCloud
 * @date 2019-05-10 10:30
 **/
@SpringBootApplication
@EnableAdminServer
@EnableEurekaClient
@EnableTurbine
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @Configuration
    public static class SecurityConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            // Page with login form is served as /login.html and does a POST on /login
            http.formLogin().loginPage("/login.html").loginProcessingUrl("/login").permitAll();
            // The UI does a POST on /logout on logout
            http.logout().logoutUrl("/logout");
            // The ui currently doesn't support csrf
            http.csrf().disable();

            // Requests for the login page and the static assets are allowed
            http.authorizeRequests()
                    .antMatchers("/login.html", "/**/*.css", "/img/**", "/third-party/**")
                    .permitAll();
            // ... and any other request needs to be authorized
            http.authorizeRequests().antMatchers("/**").authenticated();

            // Enable so that the clients can authenticate via HTTP basic for registering
            http.httpBasic();
        }
    }
}
