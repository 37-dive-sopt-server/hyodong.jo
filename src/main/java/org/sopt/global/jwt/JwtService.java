package org.sopt.global.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.sopt.global.jwt.exception.JwtErrorCode;
import org.sopt.global.jwt.exception.JwtException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Slf4j
@Service
public class JwtService {

    private final Algorithm algorithm;
    private final long accessTokenExpired;
    private final long refreshTokenExpired;

    public JwtService(
            @Value("${security.jwt.secret}") String secret,
            @Value("${security.jwt.accessTokenExpired}") long accessTokenExpired,
            @Value("${security.jwt.refreshTokenExpired}") long refreshTokenExpired
    ){
        this.algorithm = Algorithm.HMAC256(secret);
        this.accessTokenExpired = accessTokenExpired;
        this.refreshTokenExpired = refreshTokenExpired;
    }

    public String generateAccessToken(Long memberId, String email) {
        return generateToken(memberId,email, accessTokenExpired,"access");
    }


    public String generateRefreshToken(Long memberId, String email) {
        return generateToken(memberId,email,refreshTokenExpired,"refresh");
    }

    public DecodedJWT verifyToken(String token) {
        try{
            DecodedJWT decodedJWT = JWT.require(algorithm)
                    .build()
                    .verify(token);

            return decodedJWT;
        } catch(TokenExpiredException e){
            throw new JwtException(JwtErrorCode.EXPIRED_TOKEN);
        } catch(JWTVerificationException e){
            throw new JwtException(JwtErrorCode.INVALID_TOKEN);
        }
    }

    public Long getMemberIdFromToken(String token) {
        if(token == null || token.isEmpty()){
            throw new JwtException(JwtErrorCode.EMPTY_TOKEN);
        }
        try{
            DecodedJWT decodedJWT = verifyToken(token);

            String subject = decodedJWT.getSubject();

            return Long.parseLong(subject);
        } catch(NumberFormatException e){
            throw new JwtException(JwtErrorCode.INVALID_TOKEN);
        }
    }

    public String getEmailFromToken(String token) {
        if(token == null || token.isEmpty()){
            throw new JwtException(JwtErrorCode.EMPTY_TOKEN);
        }
            DecodedJWT decodedJWT = verifyToken(token);

            String email = decodedJWT.getClaim("email").asString();

            return email;
    }

    public boolean isAccessToken(String token) {
        DecodedJWT decodedJWT = verifyToken(token);
        String type = decodedJWT.getClaim("type").asString();
        return "access".equals(type);
    }

    public boolean isRefreshToken(String token) {
        DecodedJWT decodedJWT = verifyToken(token);
        String type = decodedJWT.getClaim("type").asString();
        return "refresh".equals(type);
    }

    private String generateToken(Long memberId, String email, long expiresInSeconds,String type) {
        Instant now = Instant.now();
        Instant expiration = now.plusSeconds(expiresInSeconds);

        String token = JWT.create()
                .withSubject(String.valueOf(memberId))
                .withClaim("email",email)
                .withClaim("type",type)
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(expiration))
                .sign(algorithm);

        return token;
    }

    public String extractTokenFromHeader(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return null;
        }

        return authorization.substring(7);
    }

}