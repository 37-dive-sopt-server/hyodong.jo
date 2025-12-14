package org.sopt.comment.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CommentCreateRequest(

        @NotBlank(message = "내용을 빈칸으로 둘 수 없습니다.")
        String content

) {
}
