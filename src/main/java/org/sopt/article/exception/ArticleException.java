package org.sopt.article.exception;

import org.sopt.global.exception.BusinessException;
import org.sopt.global.exception.errorcode.ErrorCode;

public class ArticleException extends BusinessException {
    public ArticleException(ErrorCode errorCode) {
        super(errorCode);
    }
}