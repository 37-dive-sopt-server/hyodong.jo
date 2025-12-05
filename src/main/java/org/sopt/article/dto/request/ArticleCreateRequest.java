package org.sopt.article.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.sopt.article.entity.Tag;

public record ArticleCreateRequest(

        @Schema(description = "제목", example = "집에 빨리 가는 법")
        @NotBlank(message = "제목은 필수입니다.")
        String title,

        @Schema(description = "내용", example = "날아간다")
        @NotBlank(message = "내용은 필수입니다.")
        String content,

        @Schema(description = "태그", example = "CS")
        @NotNull(message = "태그는 필수입니다.")
        Tag tag
) {
}