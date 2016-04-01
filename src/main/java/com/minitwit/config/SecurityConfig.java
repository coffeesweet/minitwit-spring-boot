package com.minitwit.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

// @Configuration
public class SecurityConfig {
  // extends WebSecurityConfigurerAdapter {

  // @Autowired
  // public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
  // auth.inMemoryAuthentication().withUser("barry").password("password").roles("USER"); // ... etc.
  // }

}
