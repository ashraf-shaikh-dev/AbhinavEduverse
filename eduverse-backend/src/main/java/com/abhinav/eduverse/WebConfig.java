package com.abhinav.eduverse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

// This class contains configuration related to security and CORS settings
@Configuration
public class WebConfig {

    // This method configures the security filter chain for HTTP requests
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors()  // Enable CORS support based on the corsConfigurationSource bean below
            .and()
            .csrf().disable()  // Disable CSRF protection (not needed for this API setup)
            // Allow all incoming HTTP requests without authentication (open API for now)
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());

        return http.build();  // Build and return the configured security filter chain
    }

    // This method defines the CORS configuration for the application
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // Allow requests from frontend running on localhost:3000 only
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        
        // Allow these HTTP methods from frontend
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        
        // Allow credentials like cookies or authorization headers to be included in requests
        configuration.setAllowCredentials(true);
        
        // Allow all headers from the client request
        configuration.setAllowedHeaders(List.of("*"));

        // Register the above CORS configuration for all URL paths
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        
        return source; // Return the configured source for use by Spring Security
    }
}
