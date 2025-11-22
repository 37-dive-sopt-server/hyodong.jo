package org.sopt.member.dto.response;

import org.sopt.member.entity.Member;

import java.util.List;

public record MemberListResponse(List<MemberResponse> members) {

    public static MemberListResponse from(List<Member> members) {
        List<MemberResponse> memberResponses = members.stream()
                .map(MemberResponse::from)
                .toList();
        return new MemberListResponse(memberResponses);
    }

}