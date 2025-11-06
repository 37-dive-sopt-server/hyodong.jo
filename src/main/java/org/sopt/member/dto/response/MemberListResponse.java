package org.sopt.member.dto.response;

import java.util.List;

public record MemberListResponse(List<MemberResponse> members) {

    public static MemberListResponse from(List<MemberResponse> members) {
        return new MemberListResponse(members);
    }
}