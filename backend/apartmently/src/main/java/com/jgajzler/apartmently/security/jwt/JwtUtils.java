package com.jgajzler.apartmently.security.jwt;

import com.jgajzler.apartmently.security.UserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtils {

    @Value("${application.jwt.tokenExpirationAfterMinutes}")
    private int jwtExpirationMinutes;

    @Value("${application.jwt.secretKey}")
    private String jwtSecret;

    public String generateJwtToken(Authentication authentication) {
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim("authorities", authentication.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(Date.from(Instant.from(ZonedDateTime.now().plusMinutes(jwtExpirationMinutes))))
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                .compact();
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUserNameFromJwtToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public Date getExpirationDate(String token) {
        return getClaim(token, Claims::getExpiration);
    }
}
