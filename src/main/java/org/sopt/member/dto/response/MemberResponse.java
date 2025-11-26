package org.sopt.member.dto.response;

import org.sopt.member.entity.Gender;
import org.sopt.member.entity.Member;

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


