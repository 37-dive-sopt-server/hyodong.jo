package org.sopt.auth.dto.response;

public record KakaoUserInfoResponse(

        Long id,

        String email,

        String nickname
) {
}