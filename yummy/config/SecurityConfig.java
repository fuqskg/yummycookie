package com.cookie.yummy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

//빈등록: 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것
@Configuration // IoC
public class SecurityConfig {

    @Bean
    BCryptPasswordEncoder encode() {

        return new BCryptPasswordEncoder();
    }


    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()//테스트시 걸어주는 게 좋음
                .authorizeRequests()
                    .requestMatchers("/yummy", "/yummy/auth/**", "/webtemplate/js/**", "/webtemplate/css/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated()
                .and()
                    .formLogin()
                    .loginPage("/yummy/auth/loginForm")
                    .defaultSuccessUrl("/yummy")
                .and()
                    .logout()
                    .logoutSuccessUrl("/yummy/auth/loginForm"); // 로그아웃 성공시 리다이렉트 주소


        return http.build();
    }


}