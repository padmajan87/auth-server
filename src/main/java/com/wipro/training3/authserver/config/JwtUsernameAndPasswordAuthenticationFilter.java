package com.wipro.training3.authserver.config;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wipro.training3.authserver.model.UsernameAndPasswordAuthRequest;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUsernameAndPasswordAuthenticationFilter extends 
   UsernamePasswordAuthenticationFilter{

	private AuthenticationManager authManager;
	private PasswordEncoder pwdEncoder;
	private ApplicationConfig appConfig;

	
	public JwtUsernameAndPasswordAuthenticationFilter(
			AuthenticationManager authManager,
			PasswordEncoder pwdEncoder,
			ApplicationConfig appConfig) {
	     this.authManager = authManager;
	     this.pwdEncoder = pwdEncoder;
	     this.appConfig = appConfig;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {
		try {
			UsernameAndPasswordAuthRequest authReq = new ObjectMapper().readValue(request.getInputStream(), 
					UsernameAndPasswordAuthRequest.class);
			 System.out.println("authreq::user "+authReq.getUsername()+"authreq::pwd "+authReq.getPassword());
			 Authentication authentication 
			         = new UsernamePasswordAuthenticationToken(
			        		 authReq.getUsername(),
			        		 authReq.getPassword()
			        	//	 pwdEncoder.encode(authReq.getPassword())
			        		 );
			 System.out.println("password : "+authReq.getPassword()+
					                    " pwdencoder : "+pwdEncoder.encode(authReq.getPassword()));
			 Authentication auth = authManager.authenticate(authentication);
			return auth;
			
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request
			, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		//String key = "WiproIBMpadyWiproIBMpadyWiproIBMpadyWiproIBMpadyWiproIBMpady";
	     
		String token = Jwts.builder()
	         .setSubject(authResult.getName())
	         .claim("authorities", authResult.getAuthorities())
	         .setIssuedAt(new Date())
	         .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(1)))
	         .signWith(SignatureAlgorithm.HS512, //key.getBytes()
	        		 appConfig.getJwtKey().getBytes()
	        		 )
	         .compact();
		
		response.addHeader("Authorization", "Bearer "+token);
	}
   
	
}
