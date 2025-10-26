package org.sopt.exception.handler;

import org.sopt.common.ErrorResponse;
import org.sopt.exception.custom.AgeException;
import org.sopt.exception.custom.DuplicateEmailException;
import org.sopt.exception.custom.InvalidInputException;
import org.sopt.exception.custom.MemberNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<ErrorResponse<Void>> handleMemberNotFoundException(MemberNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.fail("404",e.getMessage()));
    }

    @ExceptionHandler(AgeException.class)
    public ResponseEntity<ErrorResponse<Void>> handleAgeException(AgeException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.fail("400",e.getMessage()));
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ErrorResponse<Void>> handleDuplicateEmailException(DuplicateEmailException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.fail("400",e.getMessage()));
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ErrorResponse<Void>> handleInvalidInputException(InvalidInputException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.fail("400",e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse<Void>> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.fail("500","서버 내부 오류 발생"));
    }
}
