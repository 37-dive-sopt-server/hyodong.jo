package org.sopt.controller;

import org.sopt.dto.member.request.MemberCreateRequest;
import org.sopt.dto.member.response.MemberResponse;
import org.sopt.service.MemberService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/users")
    public MemberResponse createMember(@RequestBody MemberCreateRequest request) {

        return memberService.join(request);
    }

    @GetMapping("/users/{id}")
    public MemberResponse findMemberById(@PathVariable Long id) {
        return memberService.findOne(id);
    }

    @GetMapping("/users/all")
    public List<MemberResponse> getAllMembers() {
        return memberService.findAllMembers();
    }

    @DeleteMapping("/users/{id}")
    public void deleteMemberById(@PathVariable Long id) {
        memberService.deleteMember(id);
    }

}