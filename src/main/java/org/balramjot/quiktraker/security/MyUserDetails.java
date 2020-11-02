package org.balramjot.quiktraker.security;


import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.balramjot.quiktraker.models.User;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class MyUserDetails implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	private Long userId;
	private String firstName;
	private String email;
	private String password;
	private Boolean enabled ;
	private String role;
	private List<GrantedAuthority> authorities;
	
	public MyUserDetails(User user) {
		this.userId = user.getUserId();
		this.firstName = user.getFirstName();
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.role = user.getRole();
		this.enabled = user.getEnabled();
		this.authorities = Arrays.stream(user.getRole().split(","))
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}
	
	public Long getUserId() {
		return userId;
	}

	public String getFirstName() {
		return firstName;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public String getRole() {
		return role;
	}

}
