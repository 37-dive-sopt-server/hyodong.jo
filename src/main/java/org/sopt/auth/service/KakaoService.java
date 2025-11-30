package org.sopt.auth.service;

import lombok.RequiredArgsConstructor;
import org.sopt.auth.dto.response.KakaoUserInfoResponse;
import org.sopt.auth.exception.AuthErrorCode;
import org.sopt.auth.exception.AuthException;
import org.sopt.global.config.oauth.KakaoProperties;
import org.sopt.global.config.oauth.RestTemplateConfig;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class KakaoService {

    private final KakaoProperties kakaoProperties;  // 카카오 설정값 (REST API Key, Redirect URI)
    private final RestTemplate restTemplate; // HTTP 요청을 보내는 스프링 클래스

    public String getAccessToken(String code) {

        // 카카오 토큰 발급 API URL
        String url = "https://kauth.kakao.com/oauth/token";

        // HTTP 헤더 설정
        // 카카오 API 명세에서 요구하는 형식
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String restApiKey = kakaoProperties.getRestApiKey();
        String redirectUri = kakaoProperties.getRedirectUri();

        // 카카오 API에 보낼 파라미터 설정
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", restApiKey);
        params.add("redirect_uri", redirectUri);
        params.add("code", code);

        // HTTP 요청 엔티티 생성 (헤더 + 바디)
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        // 카카오 API에 POST 요청 전송
        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

        // 응답 body가 null -> 에러
        if (response.getBody() == null) {
            throw new AuthException(AuthErrorCode.EMPTY_TOKEN_RESPONSE);
        }

        // 응답 body에서 access_token 추출
        Map<String, Object> body = response.getBody();
        String accessToken = (String) body.get("access_token");

        return accessToken;
    }

    // 카카오 액세스 토큰으로 사용자 정보 조회
    public KakaoUserInfoResponse getUserInfo(String accessToken) {

        // 카카오 사용자 정보 조회 API URL
        String url = "https://kapi.kakao.com/v2/user/me";

        // HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        // HTTP 요청 엔티티 생성 (헤더만 존재)
        HttpEntity<Void> request = new HttpEntity<>(headers);

        // 카카오 API에 GET 요청
        ResponseEntity<Map> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                Map.class
        );

        // 응답 body가 null인지 확인
        if (response.getBody() == null) {
            throw new AuthException(AuthErrorCode.EMPTY_USER_RESPONSE);
        }

        // 응답 JSON -> MAP
        Map<String, Object> body = response.getBody();

        // kakao_account 객체 추출
        Map<String, Object> kakaoAccount = (Map<String, Object>) body.get("kakao_account");
        if (kakaoAccount == null) {
            throw new AuthException(AuthErrorCode.EMPTY_KAKAO_ACCOUNT);
        }

        // profile 객체 추출
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

        // 이메일 추출
        String email = (String) kakaoAccount.get("email");

        return new KakaoUserInfoResponse(
                (Long) body.get("id"),
                email,
                (String) profile.get("nickname")
        );

    }
}