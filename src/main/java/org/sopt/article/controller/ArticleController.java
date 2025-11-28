package org.sopt.article.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.article.dto.request.ArticleCreateRequest;
import org.sopt.article.dto.response.ArticleListResponse;
import org.sopt.article.dto.response.ArticleResponse;
import org.sopt.article.service.ArticleService;
import org.sopt.global.annotation.BusinessExceptionDescription;
import org.sopt.global.annotation.LoginMemberId;
import org.sopt.global.config.swagger.SwaggerResponseDescription;
import org.sopt.global.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/articles")
@Tag(name = "아티클", description = "아티클 작성 / 검색 등 관리 API")
public class ArticleController {

    private final ArticleService articleService;

    @Operation(summary = "아티클 작성", description = "아티클을 작성합니다.")
    @PostMapping
    @BusinessExceptionDescription(SwaggerResponseDescription.CREATE_ARTICLE)
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<ApiResponse<ArticleResponse>> createArticle(
            @LoginMemberId Long memberId,
            @Valid @RequestBody ArticleCreateRequest request
    ) {
        ArticleResponse response = articleService.createArticle(memberId,request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response));
    }

    @Operation(summary = "아티클 조회", description = "아티클을 id로 하나 조회합니다.")
    @GetMapping("/{id}")
    @BusinessExceptionDescription(SwaggerResponseDescription.GET_ARTICLE)
    public ResponseEntity<ApiResponse<ArticleResponse>> findArticle(@PathVariable Long id) {
        ArticleResponse response = articleService.findArticle(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(response));
    }

    @Operation(summary = "아티클 전체 조회", description = "전체 아티클을 조회합니다.")
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<ArticleListResponse>> findAllArticles() {
        ArticleListResponse response = articleService.findAllArticles();
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(response));
    }

    @Operation(summary = "아티클 검색", description = "아티클 제목, 작성자로 아티클을 검색합니다.")
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<ArticleListResponse>> searchArticles(@RequestParam String title,@RequestParam String name){
        ArticleListResponse response = articleService.searchArticles(title,name);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(response));
    }
}