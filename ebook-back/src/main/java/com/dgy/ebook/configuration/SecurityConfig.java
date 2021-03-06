package com.dgy.ebook.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
 
@Configuration 
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsService userDetailsService;
	 
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.cors().and()
			.authorizeRequests()
				.antMatchers("/users/register/**").permitAll()
				.antMatchers("/login").permitAll()
				.antMatchers("/books/all").permitAll()
				.antMatchers("/books/image").permitAll()
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/unauthorized").permitAll()
				.loginProcessingUrl("/login").permitAll()
				.successForwardUrl("/loginsuccess")
				.failureForwardUrl("/unauthorized").permitAll()
				.and()
			.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/loginsuccess").permitAll();
	 	http.csrf().disable();
	}

	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
         auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
    }

	@Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

}
