package org.sopt.exception.validator;

// 검증만 하는 클래스
// 객체 생성 필요 없음
public class MemberValidator {
    public static void validateName(String name){
        if(name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException("이름을 입력해주세요.");
        }
    }

    public static void validateEmailFormat(String email){
        String emailPattern="^[A-Za-z0-9]+@[A-Za-z0-9.]+$";
        if( email == null || !email.matches(emailPattern)){
            throw new IllegalArgumentException("이메일 형식이 올바르지 않습니다. 다시 입력해주세요:");
        }
    }
}
