package team.cafehub.service.image;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import team.cafehub.dto.image.ImageRequestDto;
import team.cafehub.dto.image.ImageResponseDto;

public interface ImageService {
    ImageResponseDto addImage(ImageRequestDto requestDto);

    ImageResponseDto findImageById(Long id);

    Page<ImageResponseDto> findAllImage(Pageable pageable);
}
