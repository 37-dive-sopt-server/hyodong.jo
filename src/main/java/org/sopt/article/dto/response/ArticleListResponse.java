package org.sopt.article.dto.response;

import org.sopt.article.entity.Article;

import java.util.List;

public record ArticleListResponse(List<ArticleListCommentCountResponse> articles) {

    public static ArticleListResponse from(List<Article> articles) {
        List<ArticleListCommentCountResponse> articleResponses = articles.stream()
                .map(ArticleListCommentCountResponse::from)
                .toList();

        return new ArticleListResponse(articleResponses);
    }

}
