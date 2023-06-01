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
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                )
                .authorizeHttpRequests(
                        authorize -> authorize
                                .requestMatchers("/").permitAll()
                                .requestMatchers("/register").anonymous()
                                .requestMatchers("/login").anonymous()
                                .requestMatchers("/profile").authenticated()
                                .requestMatchers("/upload").authenticated()
                                .requestMatchers("/search-result").permitAll()
                                .requestMatchers("/top-rate").permitAll()
                                .requestMatchers("/new").permitAll()
                                .requestMatchers(HttpMethod.GET,"/search").permitAll()
                                .requestMatchers(HttpMethod.POST,"/update-avatar").authenticated()
                                .requestMatchers("/error").permitAll()
                                .requestMatchers("/category").permitAll()
                                .requestMatchers("/meme-page/{memeId}").permitAll()
                                .requestMatchers("/profile/favourites").authenticated()
                                .requestMatchers("/profile/my-uploads").authenticated()
                                .requestMatchers(HttpMethod.POST,"/meme-page/{memeId}/comment").authenticated()
                                .requestMatchers(HttpMethod.POST,"/like/{memeId}").authenticated()
                                .requestMatchers(HttpMethod.POST,"/meme/{memeId}/favourite").authenticated()
                                .requestMatchers(HttpMethod.GET,"/meme/{memeId}/isFavouriteMeme").authenticated()
                                .requestMatchers(HttpMethod.GET,"/like/{memeId}/userLiked").authenticated()
                                .requestMatchers(HttpMethod.GET, "/css/**", "/images/**","/memes/**", "/avatars/**", "/favicon.ico", "/script/**").permitAll()

                                //.anyRequest().permitAll()

                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
