package team.cafehub.mapper.tag;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import team.cafehub.config.MapStructConfig;
import team.cafehub.dto.tag.TagRequestDto;
import team.cafehub.dto.tag.TagResponseDto;
import team.cafehub.model.Tag;

@Mapper(componentModel = "spring", config = MapStructConfig.class)
public interface TagMapper {
    TagResponseDto toTagResponse(Tag tag);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "tagCategory", target = "category")
    Tag toModel(TagRequestDto requestDto);
}
