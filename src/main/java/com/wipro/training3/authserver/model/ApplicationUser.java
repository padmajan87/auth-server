package com.wipro.training3.authserver.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.wipro.training3.authserver.entity.User;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


public class ApplicationUser implements UserDetails {

	private List<? extends GrantedAuthority>  grantedAuthorities;
	private String password;
	private String userName;
	private boolean isAccountNonLocked;
	private boolean isAccountNonExpired;
	private boolean isCredentialsNonExpired;
	private boolean isEnabled;

	public ApplicationUser() {}
	
	public ApplicationUser(User user) {
		this.password = user.getPassword();
		this.userName = user.getUsername();
		this.isAccountNonExpired = user.isAccountNonExpired();
		this.isAccountNonLocked = user.isAccountNonLocked();
		this.isCredentialsNonExpired = user.isCredentialsNonExpired();
		this.isEnabled = user.isEnabled();
		
		List<SimpleGrantedAuthority> authorities 
		 = new ArrayList<SimpleGrantedAuthority>();
		
		user.getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role.getRolename()));
		});
		
		this.grantedAuthorities = authorities;
	} 
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return grantedAuthorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return isAccountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return isAccountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return isCredentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return isEnabled;
	}

}
