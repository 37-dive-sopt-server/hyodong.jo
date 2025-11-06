package org.sopt.dto.member.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.sopt.domain.Gender;

public record MemberCreateRequest(@NotBlank(message = "이름은 필수 입니다.") String name,
                                  @NotBlank(message = "생년월일은 필수 입니다") String birth,
                                  @NotBlank(message = "이메일은 필수 입니다") @Email(message = "이메일 형식이 올바르지 않습니다") String email,
                                  @NotNull(message = "성별을 입력해주세요") Gender gender) {

}
