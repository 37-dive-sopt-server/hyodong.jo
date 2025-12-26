package org.sopt.comment.service;

import lombok.RequiredArgsConstructor;
import org.sopt.article.entity.Article;
import org.sopt.article.exception.ArticleErrorCode;
import org.sopt.article.exception.ArticleException;
import org.sopt.article.repository.ArticleRepository;
import org.sopt.comment.dto.request.CommentCreateRequest;
import org.sopt.comment.dto.request.CommentUpdateRequest;
import org.sopt.comment.dto.response.CommentListResponse;
import org.sopt.comment.dto.response.CommentResponse;
import org.sopt.comment.entity.Comment;
import org.sopt.comment.exception.CommentErrorCode;
import org.sopt.comment.exception.CommentException;
import org.sopt.comment.repository.CommentRepository;
import org.sopt.member.entity.Member;
import org.sopt.member.exception.MemberErrorCode;
import org.sopt.member.exception.MemberException;
import org.sopt.member.repository.MemberRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final ArticleRepository articleRepository;

    // 댓글 작성 시 아티클 상세, 아티클 목록 캐시 모두 무효화
    @Caching(evict = {
            @CacheEvict(value = "articleDetail", key = "#articleId"),
            @CacheEvict(value = "articleList", key = "'all'")
    })
    @Transactional
    public CommentResponse createComment(Long memberId, Long articleId, CommentCreateRequest request) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleException(ArticleErrorCode.ARTICLE_NOT_FOUND));

        Comment comment = Comment.create(request.content(), article, member);

        Comment savedComment = commentRepository.save(comment);

        return CommentResponse.from(savedComment);
    }

    public CommentListResponse findComment(Long articleId) {

        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleException(ArticleErrorCode.ARTICLE_NOT_FOUND));

        List<Comment> comments = commentRepository.findByArticleId(articleId);

        return CommentListResponse.from(comments);
    }

    // 댓글 수정 시 해당 아티클 캐시 삭제
    @Caching(evict = {
            @CacheEvict(value = "articleDetail", key = "#articleId"),
            @CacheEvict(value = "articleList", key = "'all'")
    })
    @Transactional
    public CommentResponse updateComment(Long memberId, Long articleId, Long commentId, CommentUpdateRequest request) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException(CommentErrorCode.COMMENT_NOT_FOUND));

        // 아티클 일치 확인
        if (!comment.getArticle().getId().equals(articleId)) {
            throw new CommentException(CommentErrorCode.COMMENT_NOT_MATCH_ARTICLE);
        }

        // 작성자 확인
        if (!comment.getMember().getId().equals(memberId)) {
            throw new CommentException(CommentErrorCode.NOT_COMMENT_OWNER);
        }

        comment.updateContent(request.content());

        return CommentResponse.from(comment);

    }

    // 댓글 삭제 시 해당 아티클 캐시 삭제
    @Caching(evict = {
            @CacheEvict(value = "articleDetail", key = "#articleId"),
            @CacheEvict(value = "articleList", key = "'all'")
    })
    @Transactional
    public void deleteComment(Long memberId, Long articleId, Long commentId) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException(CommentErrorCode.COMMENT_NOT_FOUND));

        // 게시글 일치 확인
        if (!comment.getArticle().getId().equals(articleId)) {
            throw new CommentException(CommentErrorCode.COMMENT_NOT_MATCH_ARTICLE);
        }

        // 작성자 확인
        if (!comment.getMember().getId().equals(memberId)) {
            throw new CommentException(CommentErrorCode.NOT_COMMENT_OWNER);
        }

        commentRepository.delete(comment);
    }
}
