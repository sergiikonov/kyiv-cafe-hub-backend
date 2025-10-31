package team.cafehub.dto.cafe;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public record CafeUpdateRequestDto(
        String excerpt,
        String excerptEn,
        String description,
        String descriptionEn,
        String name,
        String nameEn,
        String slug,
        String address,
        String addressEn,
        BigDecimal latitude,
        BigDecimal longitude,
        String hours,
        String hoursEn,
        Set<Long> tagIds, // ids Tags
        List<Long> imageIds
) {
}
