package team.cafehub.service.image;

import team.cafehub.dto.image.ImageRequestDto;
import team.cafehub.dto.image.ImageResponseDto;

public interface ImageService {
    ImageResponseDto addImage(ImageRequestDto requestDto);

    ImageResponseDto findImageById(Long id);
}
