package org.sopt.exception.validator;

import org.sopt.exception.custom.InvalidInputException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// 검증만 하는 클래스
// 객체 생성 필요 없음
public class MemberValidator {
    private static final String EMAIL_PATTERN="^[A-Za-z0-9]+@[A-Za-z0-9.]+$";
    public static void validateName(String name){
        if(name == null || name.trim().isEmpty()){
            throw new InvalidInputException("이름을 입력해주세요.");
        }
    }

    public static void validateEmailFormat(String email){
        if( email == null || !email.matches(EMAIL_PATTERN)){
            throw new InvalidInputException("이메일 형식이 올바르지 않습니다. 다시 입력해주세요:");
        }
    }

    public static void validateBirthFormat(String birth){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthDate = LocalDate.parse(birth, formatter);
        if(birthDate.isAfter(LocalDate.now())){
            throw new InvalidInputException("올바른 생년월일을 입력해주세요(yyyy-MM-dd 형식):");
        }
    }
}
