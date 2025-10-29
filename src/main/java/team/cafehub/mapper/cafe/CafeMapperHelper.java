package team.cafehub.mapper.cafe;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import team.cafehub.dto.cafe.CafeRequestDto;
import team.cafehub.dto.cafe.CafeUpdateRequestDto;
import team.cafehub.model.Image;
import team.cafehub.model.Tag;
import team.cafehub.model.cafe.Cafe;
import team.cafehub.repository.image.ImageRepository;
import team.cafehub.repository.tag.TagRepository;

@Component
@RequiredArgsConstructor
public class CafeMapperHelper {
    private final TagRepository tagRepository;
    private final ImageRepository imageRepository;

    public void mapTagsAndImages(CafeRequestDto dto, Cafe cafe) {
        if (dto.tagIds() != null) {
            Set<Tag> tags = new HashSet<>(tagRepository.findAllById(dto.tagIds()));
            cafe.setTags(tags);
        }

        if (dto.imageIds() != null && !dto.imageIds().isEmpty()) {
            List<Image> images = imageRepository.findAllById(dto.imageIds());

            // Встановлюємо зв'язок з кав'ярнею для кожного зображення
            images.forEach(image -> image.setCafe(cafe));

            cafe.setImages(images);
        }
    }

    public void mapTagsAndImages(CafeUpdateRequestDto dto, Cafe cafe) {
        if (dto.tagIds() != null) {
            Set<Tag> tags = new HashSet<>(tagRepository.findAllById(dto.tagIds()));
            cafe.setTags(tags);
        }

        if (dto.imageIds() != null && !dto.imageIds().isEmpty()) {
            List<Image> images = imageRepository.findAllById(dto.imageIds());
            images.forEach(image -> image.setCafe(cafe));
            cafe.setImages(images);
        }
    }
}
