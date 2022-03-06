package com.dahye.myhome.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource; // application.properties 에 설정된 것들을 주입

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/","/account/register", "/css/**","/api/**").permitAll()  // 기본 페이지는 모두 접근 가능
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                .loginPage("/account/login")
                    .permitAll()
                    .and()
                .logout()
                   .permitAll();
    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception { //스프링 내부에서 인증처리
        auth.jdbcAuthentication() //로그인
                .dataSource(dataSource)
                //.passwordEncoder(passwordEncoder())//비밀번호 암호화 자동으로
                .usersByUsernameQuery("select username,password,enabled "
                        + "from user "
                        + "where username = ?") // 인증 처리
                //join Many To many
                .authoritiesByUsernameQuery("select u.username,r.name "
                        + "from user_role ur inner join user u on ur.user_id = u.id "
                        + "inner join role r on ur.role_id = r.id "
                        + "where u.username = ?"); // 권한 처리
    }

    @Bean
    public PasswordEncoder passwordEncoder1() {
        return new BCryptPasswordEncoder();
    }
}
