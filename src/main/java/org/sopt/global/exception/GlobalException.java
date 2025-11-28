package org.sopt.global.exception;

import org.sopt.global.exception.errorcode.ErrorCode;

public class GlobalException extends BusinessException {
    public GlobalException(ErrorCode errorcode){super(errorcode);}
}
