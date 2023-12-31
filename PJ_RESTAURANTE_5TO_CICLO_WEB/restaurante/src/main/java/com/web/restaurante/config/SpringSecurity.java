package com.web.restaurante.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SpringSecurity {

	private UserDetailsService userDetailsService;
	
	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Autowired
	public void configuracionGlobal(AuthenticationManagerBuilder authentication) throws Exception {
		authentication
		.userDetailsService(userDetailsService)
		.passwordEncoder(passwordEncoder());
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable()
		  .authorizeHttpRequests(authorize -> 
		  authorize.requestMatchers("/registraUsuario/**").permitAll()
		           .requestMatchers("/index").permitAll()
		           .requestMatchers("/**").permitAll()
		           .requestMatchers("/listaUsuario").permitAll())//.hasRole("ADMIN"))
		  .formLogin(form -> form.loginPage("/login")
				  .loginProcessingUrl("/login")
				  .defaultSuccessUrl("/listaUsuario", true)
				  .permitAll())
		  .logout(logout -> logout
				  .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				  .permitAll());
		return http.build();
	} //fin de filterChain
} //fin de SpringSecurity
