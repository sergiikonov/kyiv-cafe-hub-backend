package team.cafehub.dto.cafe;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import team.cafehub.dto.image.ImageRequestDto;
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
        Set<Long> tagsIds, // ids Tags
        List<ImageRequestDto> images
) {
}
