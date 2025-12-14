package org.sopt.article.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sopt.comment.entity.Comment;
import org.sopt.member.entity.Member;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="article_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;

    private String content;

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private Tag tag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "article")
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();

    public static Article create(String title,String content,LocalDate date,Tag tag,Member member) {
        Article article = Article.builder()
                .title(title)
                .content(content)
                .date(date)
                .tag(tag)
                .member(member)
                .build();

        member.getArticles().add(article);
        return article;
    }
}