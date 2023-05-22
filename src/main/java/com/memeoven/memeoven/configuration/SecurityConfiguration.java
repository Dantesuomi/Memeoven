package com.memeoven.memeoven.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/profile", true)
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .and()
                .authorizeHttpRequests(
                        authorize -> authorize
                                .requestMatchers("/register").anonymous()
                                //.requestMatchers("/").permitAll()
                                .requestMatchers("/login").permitAll()
                                .requestMatchers("/profile").authenticated()
                                .requestMatchers("/upload").authenticated()
                                .requestMatchers(HttpMethod.GET, "/css/**", "/images/**", "/favicon.ico").permitAll()
                                .anyRequest().permitAll()
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
