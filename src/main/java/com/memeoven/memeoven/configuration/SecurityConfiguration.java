package com.memeoven.memeoven.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()
                .authorizeHttpRequests(
                    authorize -> authorize
                            .requestMatchers("/register").anonymous()
                            .requestMatchers(HttpMethod.GET,"/css/**", "/images/**", "/favicon.ico").permitAll()
                            //.anyRequest().permitAll()

                            //.requestMatchers(HttpMethod.GET, "/register").anonymous()
                            //.requestMatchers(HttpMethod.GET,"/css/**", "/js/**", "/image/**", "**/favicon.ico").permitAll()

                );

        return http.build();
    }
}
