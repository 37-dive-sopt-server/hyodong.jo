package org.sopt.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.sopt.member.entity.Gender;
import org.sopt.member.entity.Member;

public record MemberResponse(

        @Schema(description= "멤버 id", example = "1")
        Long id,

        @Schema(description = "이름", example = "조효동")
        String name,

        @Schema(description = "생년월일", example = "2001-12-01")
        String birth,

        @Schema(description = "이메일", example = "whgyehdjhd@naver.com")
        String email,

        @Schema(description = "성별", example = "MALE")
        Gender gender
) {

    public static MemberResponse from(Member member) {
        return new MemberResponse(
                member.getId(),
                member.getName(),
                member.getBirth(),
                member.getEmail(),
                member.getGender()
        );
    }
}


