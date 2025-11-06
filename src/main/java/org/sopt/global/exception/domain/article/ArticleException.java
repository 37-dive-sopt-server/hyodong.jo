package org.sopt.global.exception.domain.article;

import org.sopt.global.exception.BusinessException;
import org.sopt.global.exception.ErrorCode;

public class ArticleException extends BusinessException {
    public ArticleException(ErrorCode errorCode) {
        super(errorCode);
    }
}