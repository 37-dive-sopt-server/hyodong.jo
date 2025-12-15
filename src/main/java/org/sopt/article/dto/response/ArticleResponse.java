package org.sopt.article.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.sopt.article.entity.Article;
import org.sopt.article.entity.Tag;
import org.sopt.comment.dto.response.CommentResponse;

import java.time.LocalDate;
import java.util.List;

public record ArticleResponse(

        @Schema(description = "아티클 ID", example = "1")
        Long id,

        @Schema(description = "아티클 제목", example = "집에 빨리 가는 법")
        String title,

        @Schema(description = "아티클 내용",example = "날아간다")
        String content,

        @Schema(description = "태그", example = "CS")
        Tag tag,

        @Schema(description = "날짜", example = "2025-11-26")
        LocalDate date,

        @Schema(description = "작성자 ID", example = "1")
        Long memberId,

        @Schema(description = "작성자 이름", example = "조효동")
        String memberName,

        @Schema(description = "댓글 목록", example = "1등")
        List<CommentResponse> comments
) {
    public static ArticleResponse from(Article article) {

        List<CommentResponse> comments = article.getComments().stream()
                .map(CommentResponse::from)
                .toList();

        return new ArticleResponse(
                article.getId(),
                article.getTitle(),
                article.getContent(),
                article.getTag(),
                article.getDate(),
                article.getMember().getId(),
                article.getMember().getName(),
                comments
        );
    }
}