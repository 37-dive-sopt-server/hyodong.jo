package org.sopt.comment.exception;

import org.sopt.global.exception.errorcode.ErrorCode;
import org.springframework.http.HttpStatus;

public enum CommentErrorCode implements ErrorCode {
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND,"C001","해당 댓글을 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    CommentErrorCode(HttpStatus status, String code, String message) {
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
