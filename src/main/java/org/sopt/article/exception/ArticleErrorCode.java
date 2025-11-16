package org.sopt.article.exception;

import org.sopt.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum ArticleErrorCode implements ErrorCode {
    ARTICLE_NOT_FOUND(HttpStatus.NOT_FOUND,"A001","해당 ID의 아티클을 찾을 수 없습니다."),
    DUPLICATE_TITLE(HttpStatus.CONFLICT,"A002","이미 존재하는 제목입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ArticleErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
