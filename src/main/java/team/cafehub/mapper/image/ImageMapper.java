package team.cafehub.mapper.image;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import team.cafehub.config.MapStructConfig;
import team.cafehub.dto.image.ImageRequestDto;
import team.cafehub.dto.image.ImageResponseDto;
import team.cafehub.model.Image;

@Mapper(componentModel = "spring", config = MapStructConfig.class)
public interface ImageMapper {
    ImageResponseDto toResponse(Image image);

    @Mapping(target = "id", ignore = true)
    Image toModel(ImageRequestDto requestDto);
}
