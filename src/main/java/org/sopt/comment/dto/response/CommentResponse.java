package org.sopt.comment.dto.response;

import org.sopt.comment.entity.Comment;

public record CommentResponse(

        // 댓글 아이디
        Long id,

        // 댓글 내용
        String content,

        // 댓글 단 아티클 제목
        String articleTitle,

        // 댓글 작성자 멤버 id
        Long memberId,

        // 댓글 작성자 멤버 이름
        String memberName
) {

    public static CommentResponse from(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getArticle().getTitle(),
                comment.getMember().getId(),
                comment.getMember().getName()
        );
    }
}
