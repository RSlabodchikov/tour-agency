package com.netcracker.mano.touragency.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;


@Component
public class JwtTokenUtil implements Serializable {
    public String getLoginFromRequest(HttpServletRequest req) {
        String header = req.getHeader("Authorization");
        String authToken = header.replace(Constants.TOKEN_PREFIX, "");
        return getUsernameFromToken(authToken);
    }

    String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    private Date getExpirationDateFromToken(String token) {

        return getClaimFromToken(token, Claims::getExpiration);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(Constants.SIGNING_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(Authentication authentication) {
        final String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).
                        collect(Collectors.joining(","));
        return Jwts.builder().
                setSubject(authentication.getName())
                .claim("scopes", authorities)
                .signWith(SignatureAlgorithm.HS256, Constants.SIGNING_KEY)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()
                        + Constants.ACCESS_TOKEN_VALIDITY_SECONDS))
                .compact();

    }

    Boolean validateToken(String token, UserDetails userDetails) {
        final String login = getUsernameFromToken(token);
        return (login.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
