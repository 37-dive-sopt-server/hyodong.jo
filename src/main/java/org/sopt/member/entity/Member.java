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
public class Member{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    private String name;
    private String birth;
    private String email;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToMany(mappedBy = "member")
    private List<Article> articles = new ArrayList<>();

    private String password;

    public static Member create(String name,String birth,String email,Gender gender,String password){
        Member member = Member.builder()
                .name(name)
                .birth(birth)
                .email(email)
                .gender(gender)
                .password(password)
                .build();
        return member;
    }

    private static void validateAge(String birth){
        int age = LocalDate.now().getYear() - LocalDate.parse(birth).getYear();
        if( age < 20){
            throw new MemberException(MemberErrorCode.AGE_LOW);
        }
    }
}

