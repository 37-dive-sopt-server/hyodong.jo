package org.sopt.dto.member.response;

import org.sopt.domain.Gender;

public class MemberResponse {
    private final Long id;
    private final String name;
    private final String birth;
    private final String email;
    private final Gender gender;

    public MemberResponse(Long id, String name, String birth, String email, Gender gender) {
        this.id = id;
        this.name = name;
        this.birth = birth;
        this.email = email;
        this.gender = gender;
    }

    public Long getId() {
        return id;
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
