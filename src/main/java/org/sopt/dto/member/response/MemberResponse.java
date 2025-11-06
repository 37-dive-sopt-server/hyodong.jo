package org.sopt.dto.member.response;

import org.sopt.domain.Gender;

public record MemberResponse(Long id,String name,String birth,String email,Gender gender) {

}
