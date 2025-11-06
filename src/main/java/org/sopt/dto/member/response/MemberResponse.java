package org.sopt.dto.member.response;

import org.sopt.domain.Gender;
import org.sopt.domain.Member;

public record MemberResponse(Long id,String name,String birth,String email,Gender gender) {

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
