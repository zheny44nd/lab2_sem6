package org.example.lab2.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll() // Swagger
                // ADM for all mutating requests of base entities (except lessons)
                .requestMatchers(HttpMethod.POST, "/api/groups/**", "/api/students/**", "/api/disciplines/**", "/api/lectors/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/groups/**", "/api/students/**", "/api/disciplines/**", "/api/lectors/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/groups/**", "/api/students/**", "/api/disciplines/**", "/api/lectors/**").hasRole("ADMIN")
                // LECTOR and ADMIN can manage lessons
                .requestMatchers("/api/lessons/**").hasAnyRole("ADMIN", "LECTOR")
                // GET is allowed for all authenticated users
                .requestMatchers(HttpMethod.GET, "/api/**").authenticated()
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
