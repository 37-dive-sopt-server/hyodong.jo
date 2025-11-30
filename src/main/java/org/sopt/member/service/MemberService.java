package org.sopt.member.service;

import lombok.RequiredArgsConstructor;
import org.sopt.member.exception.MemberException;
import org.sopt.member.dto.request.MemberCreateRequest;
import org.sopt.member.dto.response.MemberListResponse;
import org.sopt.member.dto.response.MemberResponse;
import org.sopt.member.entity.Member;
import org.sopt.member.exception.MemberErrorCode;
import org.sopt.member.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService{

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public MemberResponse join(MemberCreateRequest request) {

        validateEmailDuplicate(request.email());

        String encodedPassword = passwordEncoder.encode(request.password());

        Member member = Member.create(request.name(),request.birth(), request.email(), request.gender(),encodedPassword);

        memberRepository.save(member);

        return MemberResponse.from(member);
    }

    public MemberResponse findOne(Long memberId) {

        Member member =  memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        return MemberResponse.from(member);
    }

    public MemberListResponse findAllMembers() {

        List<Member> members = memberRepository.findAll();
        return MemberListResponse.from(members);


    }

    public void deleteMember(Long memberId) {

        memberRepository.findById(memberId)
                        .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        memberRepository.deleteById(memberId);
    }


    private void validateEmailDuplicate(String email) {

        if(memberRepository.existsByEmail(email)) {
            throw new MemberException(MemberErrorCode.DUPLICATE_EMAIL);
        }
    }


}
