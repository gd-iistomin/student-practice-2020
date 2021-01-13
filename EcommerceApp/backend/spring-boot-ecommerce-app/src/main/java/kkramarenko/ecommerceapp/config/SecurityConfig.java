package kkramarenko.ecommerceapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    // We used Bcrytp to cypher stored passwords, here implement Bcrytp to match plain text pass from login form to cyphered one in db
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(
                        "SELECT username, password, 'true' from users where username = ?")
                .authoritiesByUsernameQuery("SELECT username, authority FROM users WHERE username = ?");
    }

    // permit all requests for pattern that matches http://localhost:8000/api/**
    // to allow users shop on our website without registration
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/api/**").permitAll()
                .and()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and().cors()
                .and().csrf().disable();

    }


}
