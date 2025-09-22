package team.cafehub.mapper.cafe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import team.cafehub.dto.cafe.CafeRequestDto;
import team.cafehub.model.Image;
import team.cafehub.model.Tag;
import team.cafehub.model.cafe.Cafe;
import team.cafehub.repository.tag.TagRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class CafeMapperHelper {
    @Autowired
    private TagRepository tagRepository;

    public void mapTagsAndImages(CafeRequestDto dto, Cafe cafe) {
        if (dto.tagIds() != null) {
            Set<Tag> tags = new HashSet<>(tagRepository.findAllById(dto.tagIds()));
            cafe.setTags(tags);
        }

        if (dto.images() != null) {
            List<Image> images = dto.images().stream()
                    .map(imageDto -> {
                        Image image = new Image();
                        image.setImageUrl(imageDto.imageUrl());
                        image.setAltText(imageDto.altText());
                        image.setCafe(cafe);
                        return image;
                    })
                    .toList();
            cafe.setImages(images);
        }
    }
}
