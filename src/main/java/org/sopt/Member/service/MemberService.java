package org.sopt.Member.service;

import org.sopt.Member.entity.Member;
import org.sopt.Member.dto.request.MemberCreateRequest;
import org.sopt.Member.dto.response.MemberResponse;
import org.sopt.global.exception.ErrorCode;
import org.sopt.global.exception.domain.member.MemberException;
import org.sopt.Member.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MemberService{

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    public MemberResponse join(MemberCreateRequest request) {
        if(memberRepository.existsByEmail(request.email())) {
            throw new MemberException(ErrorCode.DUPLICATE_EMAIL);
        }
        int age = LocalDate.now().getYear() - LocalDate.parse(request.birth()).getYear();
        if( age < 20){
            throw new MemberException(ErrorCode.AGE_LOW);
        }
        Member member = Member.builder()
                .name(request.name())
                .birth(request.birth())
                .email(request.email())
                .gender(request.gender())
                .build();
        memberRepository.save(member);
        return MemberResponse.from(member);
    }

    public MemberResponse findOne(Long memberId) {
        Member member =  memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));
        return MemberResponse.from(member);
    }

    public List<MemberResponse> findAllMembers() {
        return memberRepository.findAll().stream().map(MemberResponse::from).toList();
    }

    public void deleteMember(Long memberId) {
        memberRepository.findById(memberId)
                        .orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));
        memberRepository.deleteById(memberId);
    }

}
