package org.sopt.global.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "security.jwt")
public record JwtProperties(
    String secret,
    long accessTokenExpired,
    long refreshTokenExpired
) {
}
