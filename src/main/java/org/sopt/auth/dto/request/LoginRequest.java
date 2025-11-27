package org.sopt.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginRequest(

        @Schema(description = "이메일", example = "whgyehdjhd@naver.com")
        String email,

        @Schema(description = "비밀번호", example = "password")
        String password
) {
}
