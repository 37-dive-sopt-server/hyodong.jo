package org.sopt.auth.service;

import lombok.RequiredArgsConstructor;
import org.sopt.auth.dto.response.KakaoUserInfoResponse;
import org.sopt.auth.exception.AuthErrorCode;
import org.sopt.auth.exception.AuthException;
import org.sopt.global.oauth.KakaoProperties;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class KakaoService {

    private final KakaoProperties kakaoProperties;
    private final RestClient restClient;

    public String getAccessToken(String code) {

        // 카카오 토큰 발급 API URL
        String url = "https://kauth.kakao.com/oauth/token";

        String restApiKey = kakaoProperties.restApiKey();
        String redirectUri = kakaoProperties.redirectUri();

        // 카카오 API에 보낼 파라미터 설정
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", restApiKey);
        params.add("redirect_uri", redirectUri);
        params.add("code", code);

        // RestClient로 POST 요청 전송
        Map<String, Object> response = restClient.post()
                .uri(url)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(params)
                .retrieve()
                .body(Map.class);

        // 응답이 null인 경우 에러
        if (response == null) {
            throw new AuthException(AuthErrorCode.EMPTY_TOKEN_RESPONSE);
        }

        // 응답에서 access_token 추출
        String accessToken = (String) response.get("access_token");

        return accessToken;
    }

    // 카카오 액세스 토큰으로 사용자 정보 조회
    public KakaoUserInfoResponse getUserInfo(String accessToken) {

        // 카카오 사용자 정보 조회 API URL
        String url = "https://kapi.kakao.com/v2/user/me";

        // RestClient로 GET 요청
        Map<String, Object> response = restClient.get()
                .uri(url)
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .body(Map.class);

        // 응답이 null인지 확인
        if (response == null) {
            throw new AuthException(AuthErrorCode.EMPTY_USER_RESPONSE);
        }

        // kakao_account 객체 추출
        Map<String, Object> kakaoAccount = (Map<String, Object>) response.get("kakao_account");
        if (kakaoAccount == null) {
            throw new AuthException(AuthErrorCode.EMPTY_KAKAO_ACCOUNT);
        }

        // profile 객체 추출
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

        // 이메일 추출
        String email = (String) kakaoAccount.get("email");

        return new KakaoUserInfoResponse(
                (String) response.get("id"),
                email,
                (String) profile.get("nickname")
        );
    }
}