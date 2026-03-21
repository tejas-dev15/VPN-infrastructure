package com.vpn.Util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET = "my-super-secure-secret-key-1234567890";
    private final long EXPIRATION = 60*60*24*1000;

    private Key getsignkey(){
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public String GenerateToken(Long Id, String username, String role){
          return Jwts.builder()
                  .setSubject(username)
                  .claim("Id", Id)
                  .claim("role", role)
                  .setIssuedAt(new Date())
                  .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                  .signWith(getsignkey(), SignatureAlgorithm.HS256)
                  .compact();
    }

    public String extractUsername(String token){
        return getClaims(token).getSubject();
    }

    public String extractRole(String token){
        return getClaims(token).get("role", String.class);
    }

    public boolean ValidateToken(String token){
        try{
            getClaims(token);
            return true;
        }catch(JwtException e){
            return false;
        }
    }

    private Claims getClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getsignkey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
