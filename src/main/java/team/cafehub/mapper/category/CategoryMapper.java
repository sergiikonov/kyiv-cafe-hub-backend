package team.cafehub.mapper.category;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import team.cafehub.config.MapStructConfig;
import team.cafehub.dto.category.CategoryRequestDto;
import team.cafehub.dto.category.CategoryResponseDto;
import team.cafehub.model.blogpost.Category;
import team.cafehub.util.TranslationHelper;

@Mapper(componentModel = "spring", config = MapStructConfig.class,
        uses = {TranslationHelper.class})
public interface CategoryMapper {
    @Mapping(target = "name", expression = "java(helper.getTranslated(language, "
            + "category.getName(), category.getNameEn()))")
    CategoryResponseDto toResponse(Category category,
                                              @Context String language,
                                              @Context TranslationHelper helper);

    @Mapping(target = "id", ignore = true)
    Category toModel(CategoryRequestDto requestDto);
}
