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

    // == 상수화 == //
    // 카카오 토큰 발급 API URL, 카카오 사용자 정보 조회 API URL
    private static final String KAKAO_TOKEN_URL = "https://kauth.kakao.com/oauth/token";
    private static final String KAKAO_USER_INFO_URL = "https://kapi.kakao.com/v2/user/me";

    public String getAccessToken(String code) {

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
                .uri(KAKAO_TOKEN_URL)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(params)
                .retrieve()
                .body(Map.class);

        // 응답이 null인 경우 에러
        validateResponse(response);

        // 응답에서 access_token 추출
        String accessToken = safeToString(response.get("access_token"));

        if(accessToken == null) {
            throw new AuthException(AuthErrorCode.EMPTY_TOKEN_RESPONSE);
        }

        return accessToken;
    }

    // 카카오 액세스 토큰으로 사용자 정보 조회
    public KakaoUserInfoResponse getUserInfo(String accessToken) {

        // RestClient로 GET 요청
        Map<String, Object> response = restClient.get()
                .uri(KAKAO_USER_INFO_URL)
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .body(Map.class);

        // 응답이 null인지 확인
        validateResponse(response);

        // kakao_account 객체 추출
        Map<String, Object> kakaoAccount = (Map<String, Object>) response.get("kakao_account");
        if (kakaoAccount == null) {
            throw new AuthException(AuthErrorCode.EMPTY_KAKAO_ACCOUNT);
        }

        // profile 객체 추출
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

        if (profile == null) {
            throw new AuthException(AuthErrorCode.EMPTY_KAKAO_ACCOUNT);
        }

        // response 값 추출
        String id = safeToString(response.get("id"));
        String email = safeToString(kakaoAccount.get("email"));
        String nickname = safeToString(profile.get("nickname"));

        return new KakaoUserInfoResponse(id,email,nickname);
    }


    // == 내부 헬퍼 메서드 == //
    private void validateResponse(Map<String, Object> response) {
        if (response == null) {
            throw new AuthException(AuthErrorCode.EMPTY_USER_RESPONSE);
        }
    }

    // 안전하게 String으로 변환해주는 메서드
    // String.valueOf -> null도 "null"로 변환하므로 안됨
    // (String) -> ClassCastException 발생 가능
    private String safeToString(Object value) {
        if (value == null) {
            return null;
        }
        return String.valueOf(value);
    }

}