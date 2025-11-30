package org.sopt.member.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sopt.article.entity.Article;
import org.sopt.member.exception.MemberException;
import org.sopt.member.exception.MemberErrorCode;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    
    @Column(nullable = true)
    private String name;
    
    @Column(nullable = true)
    private String birth;
    
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private Gender gender;

    @OneToMany(mappedBy = "member")
    private List<Article> articles = new ArrayList<>();

    @Column(nullable = true)
    private String password;

    @Column(name = "provider")
    private String provider; // KAKAO, GOOGLE 등

    @Column(name = "provider_id")
    private String providerId; // 카카오 회원번호를 위한 필드


    public static Member create(String name, String birth, String email, Gender gender, String password) {
        Member member = Member.builder()
                .name(name)
                .birth(birth)
                .email(email)
                .gender(gender)
                .password(password)
                .provider("LOCAL")
                .build();
        return member;
    }

    public static Member createKakaoMember(String email, String nickname, String kakaoId) {
        Member member = Member.builder()
                .email(email)
                .name(nickname)
                .provider("KAKAO")
                .providerId(kakaoId)
                .password(null)
                .build();
        return member;
    }

    private static void validateAge(String birth) {
        int age = LocalDate.now().getYear() - LocalDate.parse(birth).getYear();
        if (age < 20) {
            throw new MemberException(MemberErrorCode.AGE_LOW);
        }
    }
}

