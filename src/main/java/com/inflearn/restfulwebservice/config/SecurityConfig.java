package com.inflearn.restfulwebservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration // 스프링 부트 기동 시 메모리에 해당 설정 정보를 로드
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    public void configureClobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("kenneth")
                .password("{noop}test1234") // noop : incoding 없이 사용가능하도록 함(no operation) 실무에서는 적절한 incoding 알고리즘으로 해결해야 함
                .roles("USER");
    }
}
