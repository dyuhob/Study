package com.yedam.app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests((requests) -> requests
					.antMatchers("/").permitAll()
				//.antMatchers("/", "/common", "/css/**", "/font/**", "/img/**", "/js/**", "/video/**", "/influ/main").permitAll()
				//.antMatchers("/user/**").hasAuthority("ROLE_USER")
				//.antMatchers("/seller/**").hasAuthority("ROLE_SELLER")
				//.antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
				//.antMatchers("/influ/**").hasAuthority("ROLE_USER")
				//.antMatchers("/sns/**").hasAuthority("ROLE_USER")
			)
			.formLogin((form) -> form
				.loginPage("/common/loginform")
				.defaultSuccessUrl("/common/main")
				.permitAll()
				.usernameParameter("memberId")
				.passwordParameter("memberPw")
				.successHandler(successhandler())
			)
			.logout((logout) -> logout.logoutSuccessUrl("/common/loginform").permitAll())
			.csrf().disable()
			.exceptionHandling()
			.accessDeniedPage("/common/loginform");

		return http.build();
	}
	
	@Bean
	public CustomLoginSuccessHandler successhandler() {
		return new CustomLoginSuccessHandler();
	}
}