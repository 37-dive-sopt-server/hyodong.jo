package org.sopt.controller;

import org.sopt.domain.Gender;
import org.sopt.domain.Member;
import org.sopt.service.MemberService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/users")
    public Long createMember(String name, String birth, String email, Gender gender) {

        return memberService.join(name,birth,email,gender);
    }

    @GetMapping("/users/{id}")
    public Optional<Member> findMemberById(@PathVariable Long id) {
        return memberService.findOne(id);
    }

    @GetMapping("/users/all")
    public List<Member> getAllMembers() {
        return memberService.findAllMembers();
    }

    @DeleteMapping("/users/{id}")
    public void deleteMemberById(@PathVariable Long id) {
        memberService.deleteMember(id);
    }

}