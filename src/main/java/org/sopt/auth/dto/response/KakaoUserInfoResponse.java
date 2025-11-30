package org.sopt.auth.dto.response;

public record KakaoUserInfoResponse(

        String id,

        String email,

        String nickname
) {
}