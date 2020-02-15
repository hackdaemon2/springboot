package com.lace.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 *
 * @author hackdaemon
 */
@Configuration(proxyBeanMethods = false)
public class WebConfiguration extends WebSecurityConfigurerAdapter {
  
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .antMatcher("api/**")
      .csrf()
        .disable()
      .authorizeRequests(
        (requests) ->
          requests
            .anyRequest()
            .permitAll()
      );
  }
}
