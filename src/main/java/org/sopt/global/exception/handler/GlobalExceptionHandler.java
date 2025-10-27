package org.sopt.global.exception.handler;

import org.sopt.global.response.ApiResponse;
import org.sopt.global.exception.custom.AgeException;
import org.sopt.global.exception.custom.DuplicateEmailException;
import org.sopt.global.exception.custom.InvalidInputException;
import org.sopt.global.exception.custom.MemberNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleMemberNotFoundException(MemberNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.fail("404",e.getMessage()));
    }

    @ExceptionHandler(AgeException.class)
    public ResponseEntity<ApiResponse<Void>> handleAgeException(AgeException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.fail("400",e.getMessage()));
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ApiResponse<Void>> handleDuplicateEmailException(DuplicateEmailException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.fail("400",e.getMessage()));
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidInputException(InvalidInputException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.fail("400",e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.fail("500","서버 내부 오류 발생"));
    }
}
