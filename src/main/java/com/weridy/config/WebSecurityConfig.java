package com.weridy.config;
;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity//启用Spring Security的Web安全支持
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    /**
     * 定义了哪些URL路径应该被保护
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                //添加/product/** 下的所有请求只能由user角色才能访问
                .antMatchers("/product/**").hasRole("USER")
                //添加/admin/** 下的所有请求只能由admin角色才能访问
                .antMatchers("/admin/**").hasRole("ADMIN")
                // 没有定义的请求，所有的角色都可以访问（tmp也可以）
                .anyRequest().authenticated()
                .and()
                .formLogin().and()
                .httpBasic();
        http.authorizeRequests()
                .antMatchers("/", "/home").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .and()
                .logout().permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                // 添加用户admin
                .withUser("admin")
                // 不设置密码加密
                .password("{noop}admin")
                // 添加角色为admin，user
                .roles("ADMIN", "USER")
                .and()
                // 添加用户user
                .withUser("user")
                .password("{noop}user")
                .roles("USER")
                .and()
                // 添加用户tmp
                .withUser("tmp")
                .password("{noop}tmp")
                // 没有角色
                .roles();
    }

    /**
     * 将单个用户设置在内存中
     * @param auth
     * @throws Exception
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password("password").roles("USER").and()
                        .withUser("admin").password("password").roles("USER", "ADMIN");
    }
}
