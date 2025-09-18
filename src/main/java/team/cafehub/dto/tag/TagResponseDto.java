package team.cafehub.dto.tag;

public record TagResponseDto(
        Long id,
        String name,
        String slug,
        String category
) {
}
