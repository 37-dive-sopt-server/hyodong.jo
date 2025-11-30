package org.sopt.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.sopt.member.entity.Gender;

public record MemberCreateRequest(

        @Schema(description = "이름", example = "조효동")
        @NotBlank(message = "이름은 필수 입니다.")
        String name,

        @Schema(description = "생년월일", example = "2001-12-01")
        @NotBlank(message = "생년월일은 필수 입니다")
        @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "생년월일 형식이 올바르지 않습니다. yyyy-MM-dd 형식으로 입력해주세요.")
        String birth,


        @Schema(description = "이메일", example = "whgyehdjhd@naver.com")
        @NotBlank(message = "이메일은 필수 입니다")
        @Email(message = "이메일 형식이 올바르지 않습니다")
        String email,

        @Schema(description = "성별", example = "MALE")
        @NotNull(message = "성별을 입력해주세요")
        Gender gender,

        String password
) {

}
