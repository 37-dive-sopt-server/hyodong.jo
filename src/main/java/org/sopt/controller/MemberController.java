package org.sopt.controller;

import org.sopt.domain.Gender;
import org.sopt.domain.Member;
import org.sopt.service.MemberService;
import org.sopt.service.MemberServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/users")
    public Long createMember(String name, String birth, String email, Gender gender) {

        return memberService.join(name,birth,email,gender);
    }

    @GetMapping("/users")
    public Optional<Member> findMemberById(Long id) {
        return memberService.findOne(id);
    }

    @GetMapping("/users/all")
    public List<Member> getAllMembers() {
        return memberService.findAllMembers();
    }

    @DeleteMapping("/users")
    public void deleteMemberById(Long id) {memberService.deleteMember(id);}

    public boolean existsByEmail(String email) {
        return memberService.existsByEmail(email);}
    }
