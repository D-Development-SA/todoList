package com.d_development.todoList.Security;

import com.d_development.todoList.Entity.Role;
import com.d_development.todoList.Services.Implements.UserImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthCredentials authCredentials;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        authCredentials = new AuthCredentials();
        String valueHeaderNameApp = request.getHeader("nameApp");
        String valueHeaderCodeApp = request.getHeader("codeApp");

        try{
            authCredentials = new ObjectMapper().readValue(request.getReader(), AuthCredentials.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (valueHeaderNameApp == null || valueHeaderCodeApp == null ) {

            try {
                response.getWriter().write(new ObjectMapper().writeValueAsString(getError()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken("",""));

        }else if (!(valueHeaderNameApp.equals("todoList") && valueHeaderCodeApp.equals("12345"))){

            try {
                response.getWriter().write(new ObjectMapper().writeValueAsString(getError()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken("",""));
        }

        UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(
                authCredentials.getName(),
                authCredentials.getPassword()
        );

        return getAuthenticationManager().authenticate(userToken);
    }

    private Map<String, String> getError() {
        Map<String, String> error = new HashMap<>();
        error.put("Error", "Authentication problem");
        error.put("Info", "Invalid Name or Code of the app");
        error.put("Solution", "Check headers");
        return error;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        authCredentials.setAuthorities(
                UserImpl.getUser(authResult.getName())
                        .getRoles()
                        .stream()
                        .map(Role::getRole)
                        .collect(Collectors.toList())
        );

        String token = Token.generateToken(authCredentials.getName(), authCredentials.getAuthorities());

        response.addHeader("Authorization", token);
        response.getWriter().flush();
        super.successfulAuthentication(request,response,chain,authResult);
    }
}
