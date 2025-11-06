package org.sopt.Member.controller;

import jakarta.validation.Valid;
import org.sopt.Member.dto.request.MemberCreateRequest;
import org.sopt.Member.dto.response.MemberResponse;
import org.sopt.global.response.ApiResponse;
import org.sopt.Member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/users")
    public ResponseEntity<ApiResponse<MemberResponse>> createMember(@Valid @RequestBody MemberCreateRequest request) {
        
        MemberResponse response = memberService.join(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse<MemberResponse>> findMemberById(@PathVariable Long id) {
        MemberResponse response = memberService.findOne(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(response));
    }

    @GetMapping("/users/all")
    public ResponseEntity<ApiResponse<List<MemberResponse>>> getAllMembers() {
        List<MemberResponse> responses =  memberService.findAllMembers();
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(responses));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteMemberById(@PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.success(null));
    }

}