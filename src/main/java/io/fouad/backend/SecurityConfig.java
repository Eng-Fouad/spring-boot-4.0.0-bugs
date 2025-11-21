package io.fouad.backend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) {
		http.sessionManagement(m -> m.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		    .httpBasic(HttpBasicConfigurer::disable)
		    .formLogin(FormLoginConfigurer::disable)
		    .logout(LogoutConfigurer::disable)
		    .csrf(CsrfConfigurer::disable)
		    .cors(Customizer.withDefaults())
		    .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
		return http.build();
	}
}