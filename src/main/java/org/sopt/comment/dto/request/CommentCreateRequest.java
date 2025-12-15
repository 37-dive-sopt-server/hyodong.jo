package org.sopt.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CommentCreateRequest(

        @NotBlank(message = "내용을 빈칸으로 둘 수 없습니다.")
        @Size(max = 300, message = "댓글은 300자를 초과할 수 없습니다.")
        String content

) {
}
