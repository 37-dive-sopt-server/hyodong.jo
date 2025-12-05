package org.sopt.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(

        @NotBlank(message = "이메일 입력은 필수입니다.")
        @Schema(description = "이메일", example = "whgyehdjhd@naver.com")
        String email,

        @NotBlank(message = "비밀번호 입력은 필수입니다.")
        @Schema(description = "비밀번호", example = "password")
        String password
) {
}
