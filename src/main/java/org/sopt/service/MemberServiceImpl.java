package org.sopt.service;

import org.sopt.domain.Gender;
import org.sopt.domain.Member;
import org.sopt.exception.custom.AgeException;
import org.sopt.exception.custom.DuplicateEmailException;
import org.sopt.exception.custom.MemberNotFoundException;
import org.sopt.repository.FileMemberRepository;
import org.sopt.repository.MemoryMemberRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class MemberServiceImpl implements  MemberService{

    private final MemoryMemberRepository memoryMemberRepository = new MemoryMemberRepository();
    private final FileMemberRepository fileMemberRepository = new FileMemberRepository();
    private static long sequence = 1L;


    public Long join(String name, String birth, String email, Gender gender) {
        if(fileMemberRepository.existsByEmail(email)) {
            throw new DuplicateEmailException("이미 존재하는 이메일입니다.");
        }
        int age = LocalDate.now().getYear() - LocalDate.parse(birth).getYear();
        if( age < 20){
            throw new AgeException("19세 이하는 가입이 불가능합니다.");
        }
        Member member = new Member(sequence++, name,birth,email,gender);
        fileMemberRepository.save(member);
        return member.getId();
    }

    public Optional<Member> findOne(Long memberId) {
        return fileMemberRepository.findById(memberId);
    }

    public List<Member> findAllMembers() {
        return fileMemberRepository.findAll();
    }

    public void deleteMember(Long memberId) {
        if(!fileMemberRepository.existById(memberId)) {
            throw new MemberNotFoundException(memberId);
        }
        fileMemberRepository.deleteById(memberId);
    }

    public boolean existsByEmail(String email) {
        return fileMemberRepository.existsByEmail(email);
    }
}
