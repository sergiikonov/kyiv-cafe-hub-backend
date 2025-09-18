package team.cafehub.dto.image;

public record ImageResponseDto(
        Long id,
        String imageUrl,
        String altText
) {
}
