package org.sopt.member.exception;

import org.sopt.global.exception.BusinessException;
import org.sopt.global.exception.errorcode.ErrorCode;

public class MemberException extends BusinessException {
    public MemberException(ErrorCode errorCode) {
        super(errorCode);
    }
}
