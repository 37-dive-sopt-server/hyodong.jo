package org.sopt.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.sopt.member.entity.Gender;
import org.sopt.member.entity.Member;

public record MemberResponse(

        @Schema(name = "멤버 id", description = "1")
        Long id,

        @Schema(name = "이름", description = "조효동")
        String name,

        @Schema(name = "생년월일", description = "2001-12-01")
        String birth,

        @Schema(name = "이메일", description = "whgyehdjhd@naver.com")
        String email,

        @Schema(name = "성별", description = "MALE")
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


