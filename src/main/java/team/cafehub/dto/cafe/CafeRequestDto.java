package team.cafehub.dto.cafe;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public record CafeRequestDto(
        @NotBlank
        String excerpt,
        String excerptEn,
        @NotBlank
        String description,
        String descriptionEn,
        @NotBlank
        String name,
        String nameEn,
        @NotBlank
        String slug,
        @NotBlank
        String address,
        String addressEn,
        @NotNull
        BigDecimal latitude,
        @NotNull
        BigDecimal longitude,
        @NotBlank
        String hours,
        String hoursEn,
        Set<Long> tagIds, // ids Tags
        List<Long> imageIds
) {
}
