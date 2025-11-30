package org.sopt.global.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
public class JwtTokenProvider {
    
    private final Algorithm algorithm;
    private final long accessTokenExpired;
    private final long refreshTokenExpired;
    
    public JwtTokenProvider(JwtProperties jwtProperties) {
        this.algorithm = Algorithm.HMAC256(jwtProperties.secret());
        this.accessTokenExpired = jwtProperties.accessTokenExpired();
        this.refreshTokenExpired = jwtProperties.refreshTokenExpired();
    }
    
    public String generateAccessToken(Long memberId, String email) {
        return generateToken(memberId, email, accessTokenExpired, "access");
    }
    
    public String generateRefreshToken(Long memberId, String email) {
        return generateToken(memberId, email, refreshTokenExpired, "refresh");
    }
    
    private String generateToken(Long memberId, String email, long expiresInSeconds, String type) {
        Instant now = Instant.now();
        Instant expiration = now.plusSeconds(expiresInSeconds);
        
        return JWT.create()
                .withSubject(String.valueOf(memberId))
                .withClaim("email", email)
                .withClaim("type", type)
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(expiration))
                .sign(algorithm);
    }
}
