package org.sopt.global.config.swagger;

import lombok.Getter;
import org.sopt.article.exception.ArticleErrorCode;
import org.sopt.global.exception.errorcode.ErrorCode;
import org.sopt.global.exception.errorcode.GlobalErrorCode;
import org.sopt.member.exception.MemberErrorCode;

import java.util.LinkedHashSet;
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