package org.sopt.comment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.sopt.comment.dto.request.CommentCreateRequest;
import org.sopt.comment.dto.response.CommentListResponse;
import org.sopt.comment.dto.response.CommentResponse;
import org.sopt.comment.service.CommentService;
import org.sopt.global.annotation.BusinessExceptionDescription;
import org.sopt.global.annotation.LoginMemberId;
import org.sopt.global.config.swagger.SwaggerResponseDescription;
import org.sopt.global.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
@Tag(name = "댓글", description = "댓글 작성 / 조회 등 관리 API")
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "댓글 작성", description = "로그인한 회원이 아티클에 댓글을 작성합니다")
    @PostMapping("/{articleId}")
    @BusinessExceptionDescription(SwaggerResponseDescription.CREATE_COMMENT)
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<ApiResponse<CommentResponse>> createComment(@LoginMemberId Long memberId,
                                                                          @PathVariable Long articleId,
                                                                          CommentCreateRequest request) {
        CommentResponse response = commentService.createComment(memberId,articleId,request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response));
    }

    @Operation(summary = "댓글 조회", description = "특정 아티클의 댓글을 조회합니다.")
    @GetMapping("{articleId}")
    @BusinessExceptionDescription(SwaggerResponseDescription.GET_COMMENT)
    public ResponseEntity<ApiResponse<CommentListResponse>> findComment(@PathVariable Long articleId) {
        CommentListResponse responses = commentService.findComment(articleId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(responses));
    }
}
