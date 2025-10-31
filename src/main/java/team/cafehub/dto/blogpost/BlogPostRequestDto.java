package team.cafehub.dto.blogpost;

import jakarta.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

public record BlogPostRequestDto(
        @NotBlank String title,
        String titleEn,
        String excerpt,
        String excerptEn,
        @NotBlank String content,
        String contentEn,
        @NotBlank String slug,
        Set<Long> categoryId,
        Long userId,
        Set<Long> tagIds,
        List<Long> imageIds
) {
}
