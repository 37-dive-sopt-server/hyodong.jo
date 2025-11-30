package org.sopt.global.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import org.sopt.global.exception.GlobalException;
import org.sopt.global.exception.errorcode.GlobalErrorCode;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenValidator {
    
    private final Algorithm algorithm;
    
    public JwtTokenValidator(JwtProperties jwtProperties) {
        this.algorithm = Algorithm.HMAC256(jwtProperties.secret());
    }
    
    public DecodedJWT verifyToken(String token) {
        try {
            return JWT.require(algorithm)
                    .build()
                    .verify(token);
        } catch (TokenExpiredException e) {
            throw new GlobalException(GlobalErrorCode.EXPIRED_TOKEN);
        } catch (JWTVerificationException e) {
            throw new GlobalException(GlobalErrorCode.INVALID_TOKEN);
        }
    }
    
    public Long getMemberIdFromToken(String token) {
        if (token == null || token.isEmpty()) {
            throw new GlobalException(GlobalErrorCode.EMPTY_TOKEN);
        }
        try {
            DecodedJWT decodedJWT = verifyToken(token);
            String subject = decodedJWT.getSubject();
            return Long.parseLong(subject);
        } catch (NumberFormatException e) {
            throw new GlobalException(GlobalErrorCode.INVALID_TOKEN);
        }
    }
    
    public String getEmailFromToken(String token) {
        if (token == null || token.isEmpty()) {
            throw new GlobalException(GlobalErrorCode.EMPTY_TOKEN);
        }
        DecodedJWT decodedJWT = verifyToken(token);
        return decodedJWT.getClaim("email").asString();
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
    
    public String extractTokenFromHeader(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return null;
        }
        
        return authorization.substring(7);
    }
}
