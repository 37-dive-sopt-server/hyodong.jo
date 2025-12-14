package org.sopt.comment.entity;


import jakarta.persistence.*;
import lombok.*;
import org.sopt.article.entity.Article;
import org.sopt.member.entity.Member;

@Entity
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(length = 300)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public static Comment create(String content, Article article, Member member) {
        Comment comment = Comment.builder()
                .content(content)
                .article(article)
                .member(member)
                .build();

        article.getComments().add(comment);
        member.getComments().add(comment);

        return comment;
    }
}
