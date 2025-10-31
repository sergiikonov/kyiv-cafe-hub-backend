package team.cafehub.mapper.tag;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import team.cafehub.config.MapStructConfig;
import team.cafehub.dto.tag.TagRequestDto;
import team.cafehub.dto.tag.TagResponseDto;
import team.cafehub.model.Tag;
import team.cafehub.util.TranslationHelper;

@Mapper(componentModel = "spring", config = MapStructConfig.class,
        uses = {TranslationHelper.class})
public interface TagMapper {
    @Mapping(target = "name", expression = "java(helper.getTranslated(language, "
            + "tag.getName(), tag.getNameEn()))")
    TagResponseDto toTagResponseDto(Tag tag, @Context String language,
                                       @Context TranslationHelper helper);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "tagCategory", target = "category")
    Tag toModel(TagRequestDto requestDto);
}
