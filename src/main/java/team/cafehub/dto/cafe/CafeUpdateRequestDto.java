package team.cafehub.dto.cafe;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import team.cafehub.dto.image.ImageRequestDto;

public record CafeUpdateRequestDto(
        String excerpt,
        String description,
        String name,
        String slug,
        String address,
        BigDecimal latitude,
        BigDecimal longitude,
        String hours,
        Set<Long> tagsIds, // ids Tags
        List<ImageRequestDto> images
) {
}
