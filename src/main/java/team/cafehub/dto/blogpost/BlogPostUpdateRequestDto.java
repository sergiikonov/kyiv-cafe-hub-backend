package team.cafehub.dto.blogpost;

import java.util.List;
import java.util.Set;

public record BlogPostUpdateRequestDto(
        String title,
        String excerpt,
        String content,
        String slug,
        Set<Long> categoryId,
        Long userId,
        Set<Long> tagIds,
        List<Long> imageIds
) {
}
