package com.ohgiraffers.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServlet;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenFailHandler authenFailHandler;

    public SecurityConfig(AuthenFailHandler authenFailHandler) {
        this.authenFailHandler = authenFailHandler;
    }

    @Bean // 비밀번호 암호화
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(); // 복호화,,? 10이 기본값이여서 (10) 지웠음
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        // 권한 설정 ROLE_ADMIN, ROLE_GUEST

        http.authorizeRequests()
                        .mvcMatchers("/*","/auth/login","/auth/logout").permitAll()
                        .antMatchers("/employee/list").hasAnyAuthority("ADMIN","USER")
                        .antMatchers("/employee/admin").hasAnyAuthority("ADMIN")
                        .anyRequest().authenticated() // 다른 요청이 들어왔을때 로그인 페이지로 이동
                        .and()
                        .csrf().disable();

        // 로그인 페이지 커스텀
        http.formLogin()
                .loginPage("/auth/login") // 해당 url에 post 요청을 하면 로그인 요청 처리
                .defaultSuccessUrl("/")
                .failureHandler(authenFailHandler)
                .usernameParameter("username")
                .passwordParameter("password")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout")) // 로그아웃 리소스 등록
                .deleteCookies("JSESSIONID") // 사용자 브라우저 값 지워주기
                .invalidateHttpSession(true) // 세션 처리 만료(기본적으로)
                .logoutSuccessUrl("/")
                .and()
                .sessionManagement()
                .maximumSessions(1) // 세션을 몇개까지만 설정할거야?
                .expiredUrl("/") // 요청이 한번 더 들어오면 어디로 보낼거냐?
                .maxSessionsPreventsLogin(false); // 신규값으로 로그인한 사용자의 값을 제한 ( expiredUrl로 돌아감) true일 경우 연결을 끊어버림

        return http.build();
    }
}
