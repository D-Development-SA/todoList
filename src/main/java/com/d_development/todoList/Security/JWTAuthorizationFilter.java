package com.d_development.todoList.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String bearerToken = request.getHeader("Authorization");
        String valueHeaderNameApp = request.getHeader("nameApp");
        String valueHeaderCodeApp = request.getHeader("codeApp");

        if( valueHeaderNameApp == null || valueHeaderCodeApp == null
                || !valueHeaderCodeApp.equals("12345") || !valueHeaderNameApp.equals("todoList"))
            bearerToken = "";

        if (bearerToken != null && bearerToken.startsWith("Bearer ")){
            String token = bearerToken.replace("Bearer ", "");

            SecurityContext context = SecurityContextHolder.createEmptyContext();
            Authentication authentication = Token.getAuthorizationToken(token);
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);
        }
        filterChain.doFilter(request, response);
    }
}
