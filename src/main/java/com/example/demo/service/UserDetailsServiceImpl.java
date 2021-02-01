package com.example.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.model.CustomUserDetails;
import com.example.demo.model.SiteUser;
import com.example.demo.repository.SiteUserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	private final SiteUserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username)
	        throws UsernameNotFoundException {
		SiteUser user = userRepository.findByUsername(username);
		if(user == null) {
			throw new UsernameNotFoundException(username + "not found");
		}
		return new CustomUserDetails(user);
	}
	/*public User createUserDetails(SiteUser user) {
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		//SpringSecurityで、hasRoleすると、自動で"ROLE_"が付加される為必要な処理
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
		return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
	}
	/*どこで使っているメソッドなのか？→
	 * SecurityConfig
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService)
		.passwordEncoder(passwordEncoder());
	}
	*/
}
