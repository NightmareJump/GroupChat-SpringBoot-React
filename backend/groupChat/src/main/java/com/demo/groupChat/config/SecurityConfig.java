package com.demo.groupChat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 1. 允许所有请求匿名访问
        http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        // 2. 禁用 CSRF（适用于前后端分离等REST场景）
        http.csrf(csrf -> csrf.disable());
        // （可选）禁用Basic或Form登录，否则也不会影响
        return http.build();
    }
}
