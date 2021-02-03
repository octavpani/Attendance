package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo.util.Role;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private final UserDetailsService userDetailsService;
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();

	}
	@Override
	public void configure(WebSecurity web) throws Exception {
		//画像が表示されなかったので、追加。
		web.ignoring().antMatchers("/js/**", "/css/**", "/webjars/**", "/img/**");
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/login/**","/logout/**", "/register", "/attendance", "/attendances/*", "/attendance/list/", "/delete/*", "/attendance/list*", "/export/*", "/form/*").permitAll()
			.antMatchers("/logout").authenticated()
			.antMatchers("/admin/**").hasRole(Role.ADMIN.name())
			.anyRequest().authenticated();
		http.formLogin()
			.loginPage("/login")
			.usernameParameter("username")
			.passwordParameter("password")
			.defaultSuccessUrl("/");
		http.logout()
			.logoutUrl("/logout")
			.logoutSuccessUrl("/?logout");
		http.rememberMe()
			.rememberMeParameter("rememberme")
			.tokenValiditySeconds(60*60*24*30);

	}
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService)
		.passwordEncoder(passwordEncoder());
	}

}
