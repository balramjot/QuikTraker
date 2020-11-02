package org.balramjot.quiktraker.security;

import java.util.Optional;

import org.balramjot.quiktraker.dao.UserRepoIF;
import org.balramjot.quiktraker.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
	
	@Autowired
	UserRepoIF userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> user = userRepo.findByEmail(email);
		
		user.orElseThrow(() -> new UsernameNotFoundException("Not Found: " + email));
		return user.map(MyUserDetails::new).get();
	}

}
