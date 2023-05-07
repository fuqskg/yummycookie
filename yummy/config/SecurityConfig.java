package com.cookie.yummy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

//빈등록: 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것
@Configuration // IoC
@EnableWebSecurity // 모든 요청url이 security의 제어를 받도록
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeHttpRequests().requestMatchers(
                        new AntPathRequestMatcher("/**")).permitAll()

                .and()
                .headers()
                .addHeaderWriter(new XFrameOptionsHeaderWriter(
                        XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))
                .and() //로그인
                .formLogin()
                .loginPage("/user/login")
                .defaultSuccessUrl("/")
                .and() // 로그아웃
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)

        ;
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


}