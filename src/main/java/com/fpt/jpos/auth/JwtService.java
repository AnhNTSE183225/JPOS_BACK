package com.fpt.jpos.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
// Binh
public class JwtService {

    private static final String SECRET_KEY = "5YHYOKfGQSN/DzvZgieCfh7dMOVcej+bsEePD1CKmVPtHkFHi7sXvZH48+JbfaZXbdpxX2SCe5jjxvriUlq2eBJpojTlSUI6+sOzSh4kL5if2ZEdaBDBAauUNRu5DaxtkPncW68sLO18nTkCI4AXUct8yTOF4BaLNYP7AFDsdAvNRdcda/pFKDn7jud68+BK9ixOCEOi+ZFFGfjDEBSEVZDLJYqnZ2RmSGmh1zBNhT3JUNjXEoI6K1myN4SctQgT1JbqIFi79Cay3emlphKtw3VgYPWp8bpigCf4YrfDOhn0RfoD1Ujx+NwQIeAC3FfX319GKNpuEMGtFZZIdIoLv6VqfMUwDXUlofVV9NXyjS0=\n";

    public String extractUsername(String jwt) {
        return extractClaim(jwt, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractClaims(token);
        return claimResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(
            Map<String, Object> extractClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .claims(extractClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // token last for 24hours + 1000
                .signWith(getSignInKey(), Jwts.SIG.HS256)
                .compact();

    }

    // validate if token is belong to userDetail
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
