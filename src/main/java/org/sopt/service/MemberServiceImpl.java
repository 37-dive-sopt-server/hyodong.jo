package org.sopt.service;

import org.sopt.domain.Member;
import org.sopt.dto.member.request.MemberCreateRequest;
import org.sopt.dto.member.response.MemberResponse;
import org.sopt.global.exception.ErrorCode;
import org.sopt.global.exception.domain.member.MemberException;
import org.sopt.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MemberServiceImpl implements  MemberService{

    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    public MemberResponse join(MemberCreateRequest request) {
        if(memberRepository.existsByEmail(request.getEmail())) {
            throw new MemberException(ErrorCode.DUPLICATE_EMAIL);
        }
        int age = LocalDate.now().getYear() - LocalDate.parse(request.getBirth()).getYear();
        if( age < 20){
            throw new MemberException(ErrorCode.AGE_LOW);
        }
        Long id = memberRepository.nextId();
        Member member = new Member(id,request.getName(),request.getBirth(),request.getEmail(),request.getGender());
        memberRepository.save(member);
        return mapToMemberResponse(member);
    }

    public MemberResponse findOne(Long memberId) {
        Member member =  memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));
        return mapToMemberResponse(member);
    }

    public List<MemberResponse> findAllMembers() {
        return memberRepository.findAll().stream().map(this::mapToMemberResponse).toList();
    }

    public void deleteMember(Long memberId) {
        if(!memberRepository.existById(memberId)) {
            throw new MemberException(ErrorCode.MEMBER_NOT_FOUND);
        }
        memberRepository.deleteById(memberId);
    }

    public boolean existsByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    private MemberResponse mapToMemberResponse(Member member) {
        return new MemberResponse(member.getId(),
                member.getName(),
                member.getBirth(),
                member.getEmail(),
                member.getGender());
    }
}
