package org.sopt.article.dto.response;

import org.sopt.article.entity.Article;
import org.sopt.article.entity.Tag;

import java.time.LocalDate;

public record ArticleResponse(Long id, String title, String content, Tag tag,
                              LocalDate date, Long memberId, String memberName) {
    public static ArticleResponse from(Article article) {  // 반환 타입 추가!
        return new ArticleResponse(
                article.getId(),
                article.getTitle(),
                article.getContent(),
                article.getTag(),
                article.getDate(),
                article.getMember().getId(),
                article.getMember().getName()
        );
    }
}