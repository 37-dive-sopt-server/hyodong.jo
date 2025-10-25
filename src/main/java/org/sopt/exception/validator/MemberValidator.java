package org.sopt.exception.validator;

import org.sopt.exception.custom.InvalidInputException;

// 검증만 하는 클래스
// 객체 생성 필요 없음
public class MemberValidator {
    public static void validateName(String name){
        if(name == null || name.trim().isEmpty()){
            throw new InvalidInputException("이름을 입력해주세요.");
        }
    }

    public static void validateEmailFormat(String email){
        String emailPattern="^[A-Za-z0-9]+@[A-Za-z0-9.]+$";
        if( email == null || !email.matches(emailPattern)){
            throw new InvalidInputException("이메일 형식이 올바르지 않습니다. 다시 입력해주세요:");
        }
    }
}
