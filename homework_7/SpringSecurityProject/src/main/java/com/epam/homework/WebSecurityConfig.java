package com.epam.homework;

import com.epam.homework.encoder.PasswordEncoderTest;
import com.epam.homework.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoderTest();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();

        http.authorizeRequests().antMatchers("/", "/login*", "/logout").permitAll();

        http.authorizeRequests().antMatchers("/user").hasAnyRole("USER", "ADMIN");

        http.authorizeRequests().antMatchers("/admin").hasRole("ADMIN");

        http.authorizeRequests().and().formLogin()
                .loginProcessingUrl("/perform_login")
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .permitAll()
                .usernameParameter("username")
                .passwordParameter("password")
                .and().logout().permitAll().logoutUrl("/logout").logoutSuccessUrl("/logoutSuccessful");
    }

}
