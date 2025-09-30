package team.cafehub.dto.tag;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TagResponseDto (
        Long id,
        String name,
        String slug,
        String category
) implements Serializable {
}
