package team.cafehub.dto.tag;

import jakarta.validation.constraints.NotBlank;
import team.cafehub.model.TagCategory;

public record TagRequestDto(
        @NotBlank
        String name,
        String nameEn,
        @NotBlank
        String slug,
        TagCategory tagCategory
) {
}
