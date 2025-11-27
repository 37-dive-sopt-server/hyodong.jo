package org.sopt.global.jwt.exception;

import org.sopt.global.exception.BusinessException;
import org.sopt.global.exception.errorcode.ErrorCode;

public class JwtException extends BusinessException {
    public JwtException(ErrorCode errorCode) {
        super(errorCode);
    }
}
