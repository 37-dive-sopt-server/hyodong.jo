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

    public Long getMemberIdFromAccessToken(String token) {
        if (token == null || token.isEmpty()) {
            throw new GlobalException(GlobalErrorCode.EMPTY_TOKEN);
        }

        DecodedJWT decodedJWT = verifyToken(token);

        String type = decodedJWT.getClaim("type").asString();
        if (!"access".equals(type)) {
            throw new GlobalException(GlobalErrorCode.INVALID_ACCESS_TOKEN);
        }

        try {
            return Long.parseLong(decodedJWT.getSubject());
        } catch (NumberFormatException e) {
            throw new GlobalException(GlobalErrorCode.INVALID_TOKEN);
        }
    }

    public Long getMemberIdFromRefreshToken(String token) {
        if (token == null || token.isEmpty()) {
            throw new GlobalException(GlobalErrorCode.EMPTY_TOKEN);
        }

        DecodedJWT decodedJWT = verifyToken(token);

        String type = decodedJWT.getClaim("type").asString();
        if (!"refresh".equals(type)) {
            throw new GlobalException(GlobalErrorCode.INVALID_REFRESH_TOKEN);
        }

        try {
            return Long.parseLong(decodedJWT.getSubject());
        } catch (NumberFormatException e) {
            throw new GlobalException(GlobalErrorCode.INVALID_ACCESS_TOKEN);
        }
    }

    public String getEmailFromToken(String token) {
        if (token == null || token.isEmpty()) {
            throw new GlobalException(GlobalErrorCode.EMPTY_TOKEN);
        }
        DecodedJWT decodedJWT = verifyToken(token);
        return decodedJWT.getClaim("email").asString();
    }

    public String extractTokenFromHeader(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return null;
        }

        return authorization.substring(7);
    }
}