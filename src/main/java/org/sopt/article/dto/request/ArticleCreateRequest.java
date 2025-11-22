package org.sopt.article.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.sopt.article.entity.Tag;

public record ArticleCreateRequest(@NotBlank(message = "제목은 필수입니다.") String title,
                                   @NotBlank(message = "내용은 필수입니다.") String content,
                                   @NotNull(message = "태그는 필수입니다.") Tag tag,
                                   @NotNull(message = "회원 ID는 필수입니다.") Long memberId
) {
}