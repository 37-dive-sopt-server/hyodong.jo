package org.sopt.global.oauth;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "security.kakao")
public record KakaoProperties(
        String restApiKey,
        String redirectUri
) {
}

