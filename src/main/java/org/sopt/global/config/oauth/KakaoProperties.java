package org.sopt.global.config.oauth;


import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class KakaoProperties {

    @Value("${security.kakao.restApiKey}")
    private String restApiKey;

    @Value("${security.kakao.redirectUri}")
    private String redirectUri;
}
