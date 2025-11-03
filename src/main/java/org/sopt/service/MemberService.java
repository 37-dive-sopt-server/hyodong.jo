package org.sopt.service;

import org.sopt.dto.member.request.MemberCreateRequest;
import org.sopt.dto.member.response.MemberResponse;

import java.util.List;

public interface MemberService {
    MemberResponse join(MemberCreateRequest request);

    MemberResponse findOne(Long memberId);


    List<MemberResponse> findAllMembers();

    void deleteMember(Long memberId);

    boolean existsByEmail(String email);
}
