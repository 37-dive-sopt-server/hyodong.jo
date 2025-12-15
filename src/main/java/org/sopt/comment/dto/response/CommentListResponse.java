package org.sopt.comment.dto.response;

import org.sopt.comment.entity.Comment;

import java.util.List;

public record CommentListResponse(List<CommentResponse> comments) {

    public static CommentListResponse from(List<Comment> comments){
        List<CommentResponse> commentResponses = comments.stream().
                map(CommentResponse::from)
                .toList();

        return new CommentListResponse(commentResponses);
    }


}
