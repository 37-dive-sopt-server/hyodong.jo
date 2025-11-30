package org.sopt.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record TokenResponse (

        @Schema(description = "액세스토큰", example = "~~~~")
        String accessToken,

        @Schema(description = "리프레쉬", example = "~~~~")
        String refreshToken
){
    public static TokenResponse of(String accessToken,String refreshToken){
        return new TokenResponse(accessToken,refreshToken);
    }
}
