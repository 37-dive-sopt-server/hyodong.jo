package org.sopt.service;

import org.sopt.domain.Member;
import org.sopt.dto.member.request.MemberCreateRequest;
import org.sopt.dto.member.response.MemberResponse;
import org.sopt.global.exception.custom.AgeException;
import org.sopt.global.exception.custom.DuplicateEmailException;
import org.sopt.global.exception.custom.MemberNotFoundException;
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
            throw new DuplicateEmailException("이미 존재하는 이메일입니다.");
        }
        int age = LocalDate.now().getYear() - LocalDate.parse(request.getBirth()).getYear();
        if( age < 20){
            throw new AgeException("19세 이하는 가입이 불가능합니다.");
        }
        Long id = memberRepository.nextId();
        Member member = new Member(id,request.getName(),request.getBirth(),request.getEmail(),request.getGender());
        memberRepository.save(member);
        return mapToMemberResponse(member);
    }

    public MemberResponse findOne(Long memberId) {
        Member member =  memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));
        return mapToMemberResponse(member);
    }

    public List<MemberResponse> findAllMembers() {
        return memberRepository.findAll().stream().map(this::mapToMemberResponse).toList();
    }

    public void deleteMember(Long memberId) {
        if(!memberRepository.existById(memberId)) {
            throw new MemberNotFoundException(memberId);
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
