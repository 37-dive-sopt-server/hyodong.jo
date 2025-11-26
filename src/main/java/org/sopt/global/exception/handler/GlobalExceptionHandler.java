package org.sopt.global.exception.handler;

import org.sopt.global.exception.BusinessException;
import org.sopt.global.exception.errorcode.ErrorCode;
import org.sopt.global.exception.errorcode.GlobalErrorCode;
import org.sopt.global.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 비즈니스 예외
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusinessException(BusinessException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ApiResponse.fail(errorCode.getCode(), errorCode.getMessage()));
    }

    // DTO 검증 실패
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationException(MethodArgumentNotValidException e) {

        String message = e.getBindingResult() // 검증 결과 객체
                .getFieldErrors() // 실패 필드 리스트
                .stream()
                .findFirst() // 하나만 꺼내기
                .map(error -> error.getDefaultMessage()) // DTO에서 정의한 에러메세지 추출
                .orElse(GlobalErrorCode.INVALID_INPUT.getMessage()); // 기본값

        return ResponseEntity
                .status(GlobalErrorCode.INVALID_INPUT.getStatus())
                .body(ApiResponse.fail(GlobalErrorCode.INVALID_INPUT.getCode(), message));
    }


    // 나머지 예외
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.fail(GlobalErrorCode.INTERNAL_SERVER_ERROR.getCode(),
                        GlobalErrorCode.INTERNAL_SERVER_ERROR.getMessage()));
    }
}
