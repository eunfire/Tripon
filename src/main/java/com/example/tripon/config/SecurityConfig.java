package com.example.tripon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(
                new AntPathRequestMatcher("/img/**"),
                new AntPathRequestMatcher("/css/**"),
                new AntPathRequestMatcher("/js/**"),
                new AntPathRequestMatcher("/favicon.ico"),
                new AntPathRequestMatcher("/error"));
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable) // CSRF 비활성화
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(
                            new AntPathRequestMatcher("/**")).permitAll();// 전체 액세스 허용
                    auth.requestMatchers(
                            new AntPathRequestMatcher("/post/write", "GET"),
                            new AntPathRequestMatcher("/post", "POST"),
                            new AntPathRequestMatcher("/mypage", "GET"),
                            new AntPathRequestMatcher("/adminpost", "GET"),
                            new AntPathRequestMatcher("/admin/deletePost/**", "GET"),
                            new AntPathRequestMatcher("/post/modify/**", "GET"),
                            new AntPathRequestMatcher("/post/update/**", "GET"),
                            new AntPathRequestMatcher("/admin/**", "GET"),
                            new AntPathRequestMatcher("/adminusers", "GET"),
                            new AntPathRequestMatcher("/admin/main", "GET"),
                            new AntPathRequestMatcher("/user/**", "GET"),
                            new AntPathRequestMatcher("/user/deleteUser/**", "GET"),
                            new AntPathRequestMatcher("/user/bulkDeleteUsers", "GET"),
                            new AntPathRequestMatcher("/user/authority", "GET"),
                            new AntPathRequestMatcher("/user/deleteauthority", "GET")).authenticated();//로그인한 사용자만 가능
                    auth.requestMatchers(
                            new AntPathRequestMatcher("/adminpost", "GET"),
                            new AntPathRequestMatcher("/post/report", "GET"),
                            new AntPathRequestMatcher("/admin/deletePost/**", "GET")).hasRole("ADMIN");//ADMIN권한 사용자만 가능
                })
                .formLogin(login -> login
                        .loginPage("/sign/signin")	// [A] 커스텀 로그인 페이지 지정
                        .loginProcessingUrl("/sign/signinProcess")	// [B] submit 받을 url
                        .usernameParameter("memId")	// [C] submit할 아이디
                        .passwordParameter("pw")	// [D] submit할 비밀번호
                        .defaultSuccessUrl("/Main/board", true)
                        .permitAll()
                )
                .logout(withDefaults()
                )	// 로그아웃은 기본설정으로 (/logout으로 인증해제)
        ;
        return http.build();
    }
}
