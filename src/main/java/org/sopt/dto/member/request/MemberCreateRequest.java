package org.sopt.dto.member.request;

import org.sopt.domain.Gender;

public class MemberCreateRequest {
    private String name;
    private String birth;
    private String email;
    private Gender gender;

    public MemberCreateRequest() {
    }

    public String getName() {
        return name;
    }

    public String getBirth() {
        return birth;
    }

    public String getEmail() {
        return email;
    }

    public Gender getGender() {
        return gender;
    }
}
