package org.sopt.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.global.annotation.BusinessExceptionDescription;
import org.sopt.global.config.swagger.SwaggerResponseDescription;
import org.sopt.global.response.ApiResponse;
import org.sopt.member.dto.request.MemberCreateRequest;
import org.sopt.member.dto.response.MemberListResponse;
import org.sopt.member.dto.response.MemberResponse;
import org.sopt.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "멤버", description = "멤버 등록 / 검색 / 삭제 등 관리 API")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "멤버 등록", description = "멤버 가입을 합니다")
    @PostMapping
    @BusinessExceptionDescription(SwaggerResponseDescription.CREATE_MEMBER)
    public ResponseEntity<ApiResponse<MemberResponse>> createMember(@Valid @RequestBody MemberCreateRequest request) {
        
        MemberResponse response = memberService.join(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response));
    }

    @Operation(summary = "멤버 조회", description = "id를 이용해 멤버 조회를 합니다")
    @GetMapping("/{id}")
    @BusinessExceptionDescription(SwaggerResponseDescription.GET_MEMBER)
    public ResponseEntity<ApiResponse<MemberResponse>> findMemberById(@PathVariable Long id) {
        MemberResponse response = memberService.findOne(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(response));
    }

    @Operation(summary = "모든 멤버 조회", description = "모든 멤버 조회를 합니다")
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<MemberListResponse>> getAllMembers() {
        MemberListResponse response =  memberService.findAllMembers();
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(response));
    }

    @Operation(summary = "멤버 삭제", description = "id를 이용해 멤버 삭제를 합니다")
    @DeleteMapping("/{id}")
    @BusinessExceptionDescription(SwaggerResponseDescription.DELETE_MEMBER)
    public ResponseEntity<ApiResponse<Void>> deleteMemberById(@PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.success(null));
    }

}