package org.sopt.global.exception.domain.member;

import org.sopt.global.exception.BusinessException;
import org.sopt.global.exception.ErrorCode;

public class MemberException extends BusinessException {
    public MemberException(ErrorCode errorCode) {
        super(errorCode);
    }
}
