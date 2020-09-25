package com.springboot.clienteapp;

import com.springboot.clienteapp.util.LoginSuccesMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private LoginSuccesMessage loginSuccesMessage;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests().
                antMatchers("/index","/home","/","/css/**","/js/**","/images/**").permitAll()
                .antMatchers("/views/clientes/").hasAnyRole("USER")
                .antMatchers("/views/clientes/create").hasRole("ADMIN")
                .antMatchers("/views/clientes/save").hasRole("ADMIN")
                .antMatchers("/views/clientes/edit/**").hasRole("ADMIN")
                .antMatchers("/views/clientes/delete/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .successHandler(loginSuccesMessage)
                .loginPage("/login")
                .permitAll()
                .and()
                .logout().permitAll();

    }
    @Autowired
    public void configureSecurityGlobal(AuthenticationManagerBuilder builder) throws Exception {
        builder.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder)
                .usersByUsernameQuery("")
                .authoritiesByUsernameQuery("");
    }

}
