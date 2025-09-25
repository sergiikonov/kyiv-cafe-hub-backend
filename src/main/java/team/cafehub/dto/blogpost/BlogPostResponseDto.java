package team.cafehub.dto.blogpost;

import java.util.List;
import java.util.Set;
import team.cafehub.dto.category.CategoryResponseDto;
import team.cafehub.dto.image.ImageResponseDto;
import team.cafehub.dto.tag.TagResponseDto;

public record BlogPostResponseDto(
        Long id,
        String title,
        String excerpt,
        String content,
        String slug,
        CategoryResponseDto category,
        Set<TagResponseDto> tags,
        List<ImageResponseDto> images
) {
}
