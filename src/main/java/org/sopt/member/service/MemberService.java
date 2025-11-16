package org.sopt.member.service;

import lombok.RequiredArgsConstructor;
import org.sopt.global.exception.ErrorCode;
import org.sopt.global.exception.domain.member.MemberException;
import org.sopt.member.dto.request.MemberCreateRequest;
import org.sopt.member.dto.response.MemberListResponse;
import org.sopt.member.dto.response.MemberResponse;
import org.sopt.member.entity.Member;
import org.sopt.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService{

    private final MemberRepository memberRepository;

    @Transactional
    public MemberResponse join(MemberCreateRequest request) {

        validateEmailDuplicate(request.email());

        Member member = Member.create(request.name(),request.birth(), request.email(),  request.gender());

        memberRepository.save(member);

        return MemberResponse.from(member);
    }

    public MemberResponse findOne(Long memberId) {

        Member member =  memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));

        return MemberResponse.from(member);
    }

    public MemberListResponse findAllMembers() {

        List<Member> members = memberRepository.findAll();

        return MemberListResponse.from(members);


    }

    public void deleteMember(Long memberId) {

        memberRepository.findById(memberId)
                        .orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));

        memberRepository.deleteById(memberId);
    }


    private void validateEmailDuplicate(String email) {

        if(memberRepository.existsByEmail(email)) {
            throw new MemberException(ErrorCode.DUPLICATE_EMAIL);
        }

    }


}
