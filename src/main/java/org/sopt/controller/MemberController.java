package org.sopt.controller;

import org.sopt.exception.common.ErrorResponse;
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
    public ResponseEntity<ErrorResponse<MemberResponse>> createMember(@RequestBody MemberCreateRequest request) {
        MemberValidator.validateName(request.getName());
        MemberValidator.validateEmailFormat(request.getEmail());
        MemberValidator.validateBirthFormat(request.getBirth());
        
        MemberResponse response = memberService.join(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ErrorResponse.success(response));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ErrorResponse<MemberResponse>> findMemberById(@PathVariable Long id) {
        MemberResponse response = memberService.findOne(id);
        return ResponseEntity.status(HttpStatus.OK).body(ErrorResponse.success(response));
    }

    @GetMapping("/users/all")
    public ResponseEntity<ErrorResponse<List<MemberResponse>>> getAllMembers() {
        List<MemberResponse> responses =  memberService.findAllMembers();
        return ResponseEntity.status(HttpStatus.OK).body(ErrorResponse.success(responses));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<ErrorResponse<Void>> deleteMemberById(@PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ErrorResponse.success(null));
    }

}