package team.cafehub.service.image;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.cafehub.dto.image.ImageRequestDto;
import team.cafehub.dto.image.ImageResponseDto;
import team.cafehub.exception.EntityNotFoundException;
import team.cafehub.mapper.image.ImageMapper;
import team.cafehub.repository.image.ImageRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private final ImageMapper imageMapper;

    @Override
    public ImageResponseDto addImage(ImageRequestDto requestDto) {
        var image = imageMapper.toModel(requestDto);
        return imageMapper.toResponse(imageRepository.save(image));
    }

    @Transactional(readOnly = true)
    @Override
    public ImageResponseDto findImageById(Long id) {
        var image = imageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find image by id: " + id));
        return imageMapper.toResponse(image);
    }

    @Override
    public Page<ImageResponseDto> findAllImage(Pageable pageable) {
        return imageRepository.findAll(pageable).map(imageMapper::toResponse);
    }
}
