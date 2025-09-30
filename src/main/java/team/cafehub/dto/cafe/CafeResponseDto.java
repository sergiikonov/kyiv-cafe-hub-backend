package team.cafehub.dto.cafe;

import team.cafehub.dto.image.ImageResponseDto;
import team.cafehub.dto.tag.TagResponseDto;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public record CafeResponseDto(
        Long id,
        String excerpt,
        String description,
        String name,
        String slug,
        String address,
        BigDecimal latitude,
        BigDecimal longitude,
        String hours,
        Set<TagResponseDto> tags,
        List<ImageResponseDto> images
) implements Serializable {
}
