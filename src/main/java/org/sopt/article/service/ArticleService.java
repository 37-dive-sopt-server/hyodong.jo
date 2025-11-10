package org.sopt.article.service;

import lombok.RequiredArgsConstructor;
import org.sopt.article.dto.request.ArticleCreateRequest;
import org.sopt.article.dto.response.ArticleListResponse;
import org.sopt.article.dto.response.ArticleResponse;
import org.sopt.article.entity.Article;
import org.sopt.article.repository.ArticleRepository;
import org.sopt.global.exception.ErrorCode;
import org.sopt.global.exception.domain.article.ArticleException;
import org.sopt.global.exception.domain.member.MemberException;
import org.sopt.member.entity.Member;
import org.sopt.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService  {
    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public ArticleResponse createArticle(ArticleCreateRequest request) {

        if(articleRepository.existsByTitle(request.title())){
            throw new ArticleException(ErrorCode.DUPLICATE_TITLE);
        }

        Member member = memberRepository.findById(request.memberId())
                .orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));

        Article article = Article.builder()
                .title(request.title())
                .content(request.content())
                .tag(request.tag())
                .date(LocalDate.now())
                .member(member)
                .build();

        Article savedArticle = articleRepository.save(article);

        return ArticleResponse.from(savedArticle);
    }

    @Transactional(readOnly = true)
    public ArticleResponse findArticle(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleException(ErrorCode.ARTICLE_NOT_FOUND));

        return ArticleResponse.from(article);
    }

    @Transactional(readOnly = true)
    public ArticleListResponse findAllArticles() {
        List<ArticleResponse> articles = articleRepository.findAll().stream()
                .map(ArticleResponse::from)
                .toList();

        return ArticleListResponse.from(articles);
    }

}
