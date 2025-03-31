package com.d_development.todoList.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Token {
    private final static String ACCESS_TOKEN_SECRET = "4qhq8LrEBfYcaRHxhdb9zURb2rf8e7Ud";
    public static String generateToken(String name, List<String> authorities){
        HashMap<String, Object> authority = new HashMap<>();

        for (int i = 0; i < authorities.size(); i++)
            authority.put("rol"+(i+1), authorities.get(i));

        return Jwts.builder()
                .setSubject(name)
                //.setExpiration()
                .addClaims(authority)
                .signWith(getSigningKey())
                .compact();
    }

    public static UsernamePasswordAuthenticationToken getAuthorizationToken(String token){
        List<GrantedAuthority> authorities = new ArrayList<>();

        try{
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String name = claims.getSubject();

            HashMap<String, Object> roles = new HashMap<>(claims);

            roles.forEach((key, rol) -> authorities.add(new SimpleGrantedAuthority(rol.toString())));

            return new UsernamePasswordAuthenticationToken(name, null, authorities);

        }catch (JwtException e){
            System.out.println("Can't get token");
            throw new JwtException("Can't get token");
        }
    }

    private static Key getSigningKey() {
        return Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET.getBytes());
    }
}
