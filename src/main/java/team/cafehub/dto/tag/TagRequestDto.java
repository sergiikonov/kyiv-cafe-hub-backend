package team.cafehub.dto.tag;

import team.cafehub.model.TagCategory;

public record TagRequestDto(
        String name,
        String slug,
        TagCategory tagCategory
) {
}
