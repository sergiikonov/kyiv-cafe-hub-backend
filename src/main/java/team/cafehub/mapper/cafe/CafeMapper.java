package team.cafehub.mapper.cafe;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import team.cafehub.config.MapStructConfig;
import team.cafehub.dto.cafe.CafeRequestDto;
import team.cafehub.dto.cafe.CafeResponseDto;
import team.cafehub.dto.cafe.CafeUpdateRequestDto;
import team.cafehub.model.cafe.Cafe;

@Mapper(componentModel = "spring", config = MapStructConfig.class,
        implementationName = "CafeMapperImpl")
public interface CafeMapper {
    @Mapping(target = "tags", source = "tags")
    @Mapping(target = "images", source = "images")
    CafeResponseDto toCafeResponseDto(Cafe cafe);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "images", ignore = true)
    Cafe cafeToModel(CafeRequestDto cafeRequestDto, @Context CafeMapperHelper helper);

    @AfterMapping
    default void afterCafeRequestDtoToCafe(CafeRequestDto dto,
                                           @MappingTarget Cafe cafe,
                                           @Context CafeMapperHelper helper) {
        helper.mapTagsAndImages(dto, cafe);
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "images", ignore = true)
    void updateCafeFromDto(CafeUpdateRequestDto dto, @MappingTarget Cafe cafe);
}
