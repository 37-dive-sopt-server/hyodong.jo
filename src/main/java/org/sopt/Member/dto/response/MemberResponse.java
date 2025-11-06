package org.sopt.Member.dto.response;

import org.sopt.Member.entity.Gender;
import org.sopt.Member.entity.Member;

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
