package org.sopt.comment.service;

import lombok.RequiredArgsConstructor;
import org.sopt.article.entity.Article;
import org.sopt.article.exception.ArticleErrorCode;
import org.sopt.article.exception.ArticleException;
import org.sopt.article.repository.ArticleRepository;
import org.sopt.comment.dto.request.CommentCreateRequest;
import org.sopt.comment.dto.response.CommentListResponse;
import org.sopt.comment.dto.response.CommentResponse;
import org.sopt.comment.entity.Comment;
import org.sopt.comment.repository.CommentRepository;
import org.sopt.member.entity.Member;
import org.sopt.member.exception.MemberErrorCode;
import org.sopt.member.exception.MemberException;
import org.sopt.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final ArticleRepository articleRepository;

    public CommentResponse createComment(Long memberId, Long articleId, CommentCreateRequest request) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleException(ArticleErrorCode.ARTICLE_NOT_FOUND));

        Comment comment = Comment.create(request.content(),article,member);

        Comment savedComment = commentRepository.save(comment);

        return CommentResponse.from(savedComment);
    }

    @Transactional(readOnly = true)
    public CommentListResponse findComment(Long articleId){

        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleException(ArticleErrorCode.ARTICLE_NOT_FOUND));

        List<Comment> comments = commentRepository.findByArticleId(articleId);

        return CommentListResponse.from(comments);
    }
}
