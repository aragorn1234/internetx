package com.internetx.security;

import com.internetx.domain.Role;
import com.internetx.domain.User;
import com.internetx.service.IUserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Slf4j
@Component
public class JwtTokenProvider {

    @Value("{app.jwtSecret")
    protected String jwtSecret;

    @Autowired
    protected IUserService userService;

    public String generateToken(String userEmail) {

        Optional<User> user = userService.findUserByEmail(userEmail);
        Role role = null;

        if (user.isPresent()) {

            role = user.get().getRole();

        }


        Instant now = Instant.now();
        Instant expiration = now.plus(7, ChronoUnit.DAYS);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = "";
        try {
            json = ow.writeValueAsString(role);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        json = json.replaceAll("\\n", "").replaceAll("\"", "\'");

        Claims claims = Jwts.claims().setSubject(json);
        claims.put("roles",json);


        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userEmail)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiration))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String generateToken(Authentication authentication) {
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User)authentication.getPrincipal();
        return generateToken(user.getUsername());
    }

    public String getUserMailFromToken(String token) {

        Claims claims = Jwts.parser()
                            .setSigningKey(jwtSecret)
                            .parseClaimsJws(token)
                            .getBody();

        return claims.getSubject();

    }





    public boolean tokenIsValid(String token) {

        try {

            Jwts.parser().setSigningKey(jwtSecret)
                    .parseClaimsJws(token);
            return true;

        } catch (SignatureException ex) {

            log.error("Invalid JWT signature!");

        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token!");
        } catch(ExpiredJwtException ex) {
            log.error("Expirted JWT token!");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token!");
        } catch (IllegalArgumentException ex) {
            log.error("string is emptry");
        }

        return false;

    }



}
