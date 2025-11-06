package org.sopt.dto.member.request;

import org.sopt.domain.Gender;

public record MemberCreateRequest(String name,String birth,String email,Gender gender) {

}
