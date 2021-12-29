package com.wipro.training3.authserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.wipro.training3.authserver.service.ApplicationUserService;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	private PasswordEncoder passwordencoder;
	private ApplicationUserService appUserSer;
	private ApplicationConfig appConfig;
	public SecurityConfig(PasswordEncoder passwordencoder,
			ApplicationUserService appUserSer,
			ApplicationConfig appConfig) {
		this.passwordencoder = passwordencoder;
		this.appUserSer = appUserSer;
		this.appConfig = appConfig;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		      .authorizeRequests()
		      .antMatchers("/**/addUser")
		      .permitAll()
		      .and()
		    .sessionManagement()
	          .sessionCreationPolicy(
	                		SessionCreationPolicy.STATELESS)
	          .and()
	          .addFilter(
	    		new JwtUsernameAndPasswordAuthenticationFilter(
	    				authenticationManager(),passwordencoder,
	    				appConfig))
		    .authorizeRequests()
		      .anyRequest()
		      .authenticated();
     }

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      auth.authenticationProvider(daoAuthenticationProvider());
	}

	private AuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider daoAuthProvider = new DaoAuthenticationProvider();
		daoAuthProvider.setPasswordEncoder(passwordencoder);
		daoAuthProvider.setUserDetailsService(appUserSer);
		return daoAuthProvider;
	}
	
	
	
}
