package org.example;

import java.rmi.server.ServerCloneException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity

public class SecurityConfig {

    @Bean 
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean 
    public UserDetailsService userDetailsService(PasswordEncoder encoder ){
        var user= User.builder()
        .username("admin")
        .password(encoder.encode("Password123"))
        .roles("USER")
        .build();

        return new InMemoryUserDetailsManager(user);


    }

    @Bean 
   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfig()))
            .csrf(csrf -> csrf.disable()) // Desactivar para API REST
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/login").permitAll()  // Login es público
                .requestMatchers("/api/public/**").permitAll() // Rutas públicas
                .anyRequest().authenticated()  // Todo lo demás: requiere login
            )
            .httpBasic(basic -> basic.disable()) // No usar login por defecto de Spring
            .formLogin(form -> form.disable());  // No usar formulario de Spring

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfig() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("https://arepeci2026-1.duckdns.org");
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    
}
