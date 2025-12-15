package org.sopt.comment.exception;

import org.sopt.global.exception.BusinessException;
import org.sopt.global.exception.errorcode.ErrorCode;

public class CommentException extends BusinessException {
    public CommentException(ErrorCode errorCode) {
        super(errorCode);
    }
}
