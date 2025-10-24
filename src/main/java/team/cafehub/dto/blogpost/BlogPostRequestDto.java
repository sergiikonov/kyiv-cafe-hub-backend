package team.cafehub.dto.blogpost;

import jakarta.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

public record BlogPostRequestDto(
        @NotBlank String title,
        String excerpt,
        @NotBlank String content,
        @NotBlank String slug,
        Set<Long> categoryId,
        Long userId,
        Set<Long> tagIds,
        List<Long> imageIds
) {
}
