package org.sopt.article.dto.response;

import org.sopt.article.entity.Article;

import java.util.List;

public record ArticleListResponse(List<ArticleResponse> articles) {

    public static ArticleListResponse from(List<Article> articles) {
        List<ArticleResponse> articleResponses = articles.stream()
                .map(ArticleResponse::from)
                .toList();

        return new ArticleListResponse(articleResponses);
    }

}
