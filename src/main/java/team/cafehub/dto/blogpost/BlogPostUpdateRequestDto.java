package team.cafehub.dto.blogpost;

import java.util.List;
import java.util.Set;

public record BlogPostUpdateRequestDto(
        String title,
        String titleEn,
        String excerpt,
        String excerptEn,
        String content,
        String contentEn,
        String slug,
        Set<Long> categoryId,
        Long userId,
        Set<Long> tagIds,
        List<Long> imageIds
) {
}
