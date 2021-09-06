package com.TFG.tempo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired
  private AuthenticationServiceImpl authenticationService;

  @Autowired
  public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(authenticationService);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.httpBasic().and().authorizeRequests().anyRequest().authenticated()
        .and().csrf().disable().headers().frameOptions().disable();
    http.cors();
  }
}
