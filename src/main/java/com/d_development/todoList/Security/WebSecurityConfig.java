package com.d_development.todoList.Security;

import com.d_development.todoList.Services.Implements.UserImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.List;


@Configuration
@AllArgsConstructor
public class WebSecurityConfig {

    private final UserImpl user;
    private final JWTAuthorizationFilter jwtAuthorizationFilter;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity, AuthenticationManager manager) throws Exception {
        JWTAuthenticationFilter jwtAuthenticationFilter = new JWTAuthenticationFilter();
        jwtAuthenticationFilter.setAuthenticationManager(manager);
        jwtAuthenticationFilter.setFilterProcessesUrl("/login");

        return httpSecurity.csrf().disable()
                .cors(corsConfig -> {
                    corsConfig.configurationSource(request -> {
                        CorsConfiguration corsConfiguration = new CorsConfiguration();

                        corsConfiguration.setAllowedOrigins(List.of("http://localhost:4200"));
                        corsConfiguration.setAllowedMethods(Arrays.asList(
                                "GET","POST","PUT","DELETE"
                        ));
                        corsConfiguration.setAllowedHeaders(Arrays.asList(
                                "Authorization", "Content-Type", "codeApp", "nameApp"
                        ));

                        corsConfiguration.setExposedHeaders(List.of(
                                "Authorization"
                        ));

                        return corsConfiguration;
                    });
                })
                .authorizeHttpRequests()
/*
                .requestMatchers(HttpMethod.POST, "/api/usuarios/crearUsuario")
                .permitAll()
                .requestMatchers("/api/usuarios", "/api/personas")
                .hasRole("ADMIN")
                .requestMatchers("/api/usuarios/**", "/api/personas/**")
                .hasRole("ADMIN")
                .requestMatchers("/api/gastosIngresos/**", "/api/PDF/**")
                .hasAnyRole("ADMIN", "USER")
                .requestMatchers("/api/gastosIngresos")
                .hasAnyRole("ADMIN", "USER")
*/
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(jwtAuthenticationFilter)
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(user)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
