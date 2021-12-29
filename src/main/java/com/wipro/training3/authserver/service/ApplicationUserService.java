package com.wipro.training3.authserver.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wipro.training3.authserver.entity.User;
import com.wipro.training3.authserver.model.ApplicationUser;
import com.wipro.training3.authserver.repository.UserRepo;


@Service
public class ApplicationUserService implements UserDetailsService{

	private  UserRepo userRepo;
	private  PasswordEncoder pwdEncoder;
	
	public ApplicationUserService(UserRepo userRepo,PasswordEncoder pwdEncoder) {
		this.pwdEncoder = pwdEncoder;
		this.userRepo = userRepo;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) 
			throws UsernameNotFoundException {
		
		User userDtls = userRepo.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(
						String.format("Username %s not found", username)));
		
		ApplicationUser appUser = new ApplicationUser(userDtls);
		return appUser;
	}

	public void save(User user) {
		user.setPassword(pwdEncoder.encode(user.getPassword()));
		userRepo.save(user);
		
	}

}
