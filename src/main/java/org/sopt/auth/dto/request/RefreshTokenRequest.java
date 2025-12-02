package org.sopt.auth.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequest(

        @NotBlank(message = "리프레쉬 토큰은 필수입니다.")
        String refreshToken
) {
}
