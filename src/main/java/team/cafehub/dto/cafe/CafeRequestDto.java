package team.cafehub.dto.cafe;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public record CafeRequestDto(
        @NotBlank
        String excerpt,
        @NotBlank
        String description,
        @NotBlank
        String name,
        @NotBlank
        String slug,
        @NotBlank
        String address,
        @NotNull
        BigDecimal latitude,
        @NotNull
        BigDecimal longitude,
        @NotBlank
        String hours,
        Set<Long> tagIds, // ids Tags
        List<Long> imageIds
) {
}
