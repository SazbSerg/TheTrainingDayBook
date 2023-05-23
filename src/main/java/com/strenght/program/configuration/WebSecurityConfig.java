package com.strenght.program.configuration;

import com.strenght.program.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import java.util.concurrent.TimeUnit;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public WebSecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(8);
    }



    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/*", "/js/*", "/logout", "/login", "/static/**", "/registration", "/registration**", "/registration-confirm/**", "/", "/index").permitAll()
                        //.mvcMatchers("/", "index", "/css/*", "/js/*", "/logout", "/login").permitAll()
                        .anyRequest().authenticated()
                )
                .userDetailsService(userDetailsService)
                .headers(headers -> headers.frameOptions().sameOrigin())
                .formLogin().loginPage("/login").permitAll()
                .defaultSuccessUrl("/main", true)
                .usernameParameter("email")
                .passwordParameter("pass")
                .and()
                .rememberMe()
                .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30))
                .key("somethingverysecured")
                .rememberMeParameter("remember-me")
                .and().build();

    }

    // .logout((logout) -> logout.permitAll());

}