package org.sopt.global.exception.custom;

public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException(Long id) {
        super("해당 ID의 회원이 존재하지 않습니다. (ID: " + id + ")");
    }
}
