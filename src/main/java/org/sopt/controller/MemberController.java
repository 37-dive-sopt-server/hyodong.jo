package org.sopt.controller;

import org.sopt.common.ApiResponse;
import org.sopt.dto.member.request.MemberCreateRequest;
import org.sopt.dto.member.response.MemberResponse;
import org.sopt.exception.validator.MemberValidator;
import org.sopt.service.MemberService;
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
    public ResponseEntity<ApiResponse<MemberResponse>> createMember(@RequestBody MemberCreateRequest request) {
        MemberValidator.validateName(request.getName());
        MemberValidator.validateEmailFormat(request.getEmail());
        
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