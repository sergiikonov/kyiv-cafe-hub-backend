package team.cafehub.mapper.category;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import team.cafehub.config.MapStructConfig;
import team.cafehub.dto.category.CategoryRequestDto;
import team.cafehub.dto.category.CategoryResponseDto;
import team.cafehub.model.blog_post.Category;

@Mapper(componentModel = "spring", config = MapStructConfig.class)
public interface CategoryMapper {
    CategoryResponseDto toResponse(Category category);

    @Mapping(target = "id", ignore = true)
    Category toModel(CategoryRequestDto requestDto);
}
