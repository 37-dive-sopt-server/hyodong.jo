package org.sopt.article.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.article.dto.request.ArticleCreateRequest;
import org.sopt.article.dto.response.ArticleListResponse;
import org.sopt.article.dto.response.ArticleResponse;
import org.sopt.article.entity.Article;
import org.sopt.article.exception.ArticleErrorCode;
import org.sopt.article.exception.ArticleException;
import org.sopt.article.repository.ArticleRepository;
import org.sopt.member.entity.Member;
import org.sopt.member.exception.MemberErrorCode;
import org.sopt.member.exception.MemberException;
import org.sopt.member.repository.MemberRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleService  {
    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;

    @Transactional
    @CacheEvict(value = "articleList", key="'all'")
    public ArticleResponse createArticle(Long memberId, ArticleCreateRequest request) {

        validateTitleExists(request.title());

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        Article article = Article.create(request.title(), request.content(),LocalDate.now(),request.tag(),member);

        Article savedArticle = articleRepository.save(article);

        return ArticleResponse.from(savedArticle);

    }

    // 아티클 상세조회 (댓글 포함) 캐싱
    @Cacheable(value = "articleDetail", key = "#articleId")
    public ArticleResponse findArticle(Long articleId) {

        log.info(">>>> [Cache Miss - DB Access] findArticle method executed for articleId: {}", articleId);

        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleException(ArticleErrorCode.ARTICLE_NOT_FOUND));

        return ArticleResponse.from(article);

    }

    // 아티클 전체 조회 (댓글 개수만) 캐싱
    @Cacheable(value = "articleList", key = "'all'")
    public ArticleListResponse findAllArticles() {

        List<Article> articles = articleRepository.findAll();

        return ArticleListResponse.from(articles);
    }

    public ArticleListResponse searchArticles(String title,String name) {

        List<Article> articles = articleRepository.findByTitleContainingAndMember_NameContaining(title,name);

        return ArticleListResponse.from(articles);

    }

    private void validateTitleExists(String title) {

        if(articleRepository.existsByTitle(title)){
            throw new ArticleException(ArticleErrorCode.DUPLICATE_TITLE);
        }

    }

}
