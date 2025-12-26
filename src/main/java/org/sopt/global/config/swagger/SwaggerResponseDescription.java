package org.sopt.global.config.swagger;

import lombok.Getter;
import org.sopt.article.exception.ArticleErrorCode;
import org.sopt.auth.exception.AuthErrorCode;
import org.sopt.comment.entity.Comment;
import org.sopt.comment.exception.CommentErrorCode;
import org.sopt.global.exception.errorcode.ErrorCode;
import org.sopt.global.exception.errorcode.GlobalErrorCode;
import org.sopt.member.exception.MemberErrorCode;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

@Getter
// 에러 그룹
public enum SwaggerResponseDescription {

    GET_MEMBER(new LinkedHashSet<>(Set.of(
            MemberErrorCode.MEMBER_NOT_FOUND
    ))),

    CREATE_MEMBER(new LinkedHashSet<>(Set.of(
            MemberErrorCode.DUPLICATE_EMAIL,
            MemberErrorCode.AGE_LOW
    ))),

    DELETE_MEMBER(new LinkedHashSet<>(Set.of(
            MemberErrorCode.MEMBER_NOT_FOUND
    ))),

    CREATE_ARTICLE(new LinkedHashSet<>(Set.of(
            ArticleErrorCode.DUPLICATE_TITLE,
            MemberErrorCode.MEMBER_NOT_FOUND
    ))),

    GET_ARTICLE(new LinkedHashSet<>(Set.of(
            ArticleErrorCode.ARTICLE_NOT_FOUND
    ))),

    REQUEST_LOGIN(new LinkedHashSet<>(Set.of(
            AuthErrorCode.INVALID_PASSWORD
    ))),

    CREATE_COMMENT(new LinkedHashSet<>(Set.of(
            MemberErrorCode.MEMBER_NOT_FOUND,
            ArticleErrorCode.ARTICLE_NOT_FOUND,
            CommentErrorCode.COMMENT_NOT_FOUND
    ))),

    GET_COMMENT(new LinkedHashSet<>(Set.of(
            ArticleErrorCode.ARTICLE_NOT_FOUND
    ))),

    UPDATE_COMMENT(new LinkedHashSet<>(Set.of(
             CommentErrorCode.COMMENT_NOT_FOUND,
             CommentErrorCode.COMMENT_NOT_MATCH_ARTICLE,
             CommentErrorCode.NOT_COMMENT_OWNER
    ))),

    DELETE_COMMENT(new LinkedHashSet<>(Set.of(
            CommentErrorCode.COMMENT_NOT_FOUND,
            CommentErrorCode.COMMENT_NOT_MATCH_ARTICLE,
            CommentErrorCode.NOT_COMMENT_OWNER
    )));

    private final Set<ErrorCode> errorCodeList;

    SwaggerResponseDescription(Set<ErrorCode> specificErrorCodes) {
        this.errorCodeList = new LinkedHashSet<>();
        this.errorCodeList.addAll(specificErrorCodes);
        this.errorCodeList.addAll(getGlobalErrorCodes());
    }

    private Set<ErrorCode> getGlobalErrorCodes() {
        return new LinkedHashSet<>(Set.of(GlobalErrorCode.values()));
    }
}