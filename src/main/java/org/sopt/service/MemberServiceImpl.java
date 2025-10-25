package org.sopt.service;

import org.sopt.domain.Gender;
import org.sopt.domain.Member;
import org.sopt.exception.custom.AgeException;
import org.sopt.exception.custom.DuplicateEmailException;
import org.sopt.exception.custom.MemberNotFoundException;
import org.sopt.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MemberServiceImpl implements  MemberService{

    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    public Long join(String name, String birth, String email, Gender gender) {
        if(memberRepository.existsByEmail(email)) {
            throw new DuplicateEmailException("이미 존재하는 이메일입니다.");
        }
        int age = LocalDate.now().getYear() - LocalDate.parse(birth).getYear();
        if( age < 20){
            throw new AgeException("19세 이하는 가입이 불가능합니다.");
        }
        Long id = memberRepository.nextId();
        Member member = new Member(id, name,birth,email,gender);
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
            throw new MemberNotFoundException(memberId);
        }
        memberRepository.deleteById(memberId);
    }

    public boolean existsByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }
}
