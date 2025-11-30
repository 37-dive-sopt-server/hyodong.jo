package org.sopt.auth.exception;

import lombok.Getter;
import org.sopt.global.exception.errorcode.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
public enum AuthErrorCode implements ErrorCode {
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST,"A001"," 비밀번호가 일치하지 않습니다."),
    ALREADY_REGISTERED_EMAIL(HttpStatus.BAD_REQUEST,"A002","이미 가입된 이메일입니다. 일반 로그인을 해주세요"),

    // 카카오 로그인 에러
    EMPTY_TOKEN_RESPONSE(HttpStatus.NO_CONTENT,"K001","카카오 토큰 발급 응답이 비어있습니다."),
    EMPTY_USER_RESPONSE(HttpStatus.NO_CONTENT,"K002","카카오 사용자 정보 응답이 비어있습니다."),
    EMPTY_KAKAO_ACCOUNT(HttpStatus.NO_CONTENT,"K003","카카오 계정 정보를 가져올 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    AuthErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

}
