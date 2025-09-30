package team.cafehub.dto.image;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ImageResponseDto(
        Long id,
        String imageUrl,
        String altText
) implements Serializable {
}
