package com.inflearn.restfulwebservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration // 스프링 부트 기동 시 메모리에 해당 설정 정보를 로드
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /*H2 databse 사용을 위해 인증 무시*/
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // /h2-console/* 모두 허가
        http.authorizeRequests().antMatchers("/h2-console/*").permitAll();
        // 크로스 사이트 스크립트 사용 중지
        http.csrf().disable();
        // 헤더값의 프레임 속성값 사용 중지
        http.headers().frameOptions().disable();
    }

    @Autowired
    public void configureClobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("kenneth")
                .password("{noop}test1234") // noop : incoding 없이 사용가능하도록 함(no operation) 실무에서는 적절한 incoding 알고리즘으로 해결해야 함
                .roles("USER");
    }
}
