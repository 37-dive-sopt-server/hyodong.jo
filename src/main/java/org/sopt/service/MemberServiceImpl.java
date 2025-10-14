package org.sopt.service;

import org.sopt.domain.Gender;
import org.sopt.domain.Member;
import org.sopt.repository.MemoryMemberRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class MemberServiceImpl implements  MemberService{

    private final MemoryMemberRepository memberRepository = new MemoryMemberRepository();
    private static long sequence = 1L;

    public Long join(String name, String birth, String email, Gender gender) {
        int age = LocalDate.now().getYear() - LocalDate.parse(birth).getYear();
        if( age < 20){
            throw new IllegalArgumentException("❌ 19세 이하는 가입이 불가능합니다.");
        }
        Member member = new Member(sequence++, name,birth,email,gender);
        memberRepository.save(member);
        return member.getId();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }

    public void deleteMember(Long memberId) {
        if(!memberRepository.existById(memberId)) {
            throw new IllegalArgumentException("⚠️ 해당 ID의 회원이 존재하지 않습니다.");
        }
        memberRepository.deleteById(memberId);
    }
}
