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
import team.cafehub.util.TranslationHelper;

@Mapper(componentModel = "spring", config = MapStructConfig.class,
        implementationName = "CafeMapperImpl",
        uses = {CafeMapperHelper.class, TranslationHelper.class})
public interface CafeMapper {
    @Mapping(target = "name", expression = "java(helper.getTranslated(language, "
            + "cafe.getName(), cafe.getNameEn()))")
    @Mapping(target = "description", expression = "java(helper.getTranslated(language, "
            + "cafe.getDescription(), cafe.getDescriptionEn()))")
    @Mapping(target = "excerpt", expression = "java(helper.getTranslated(language, "
            + "cafe.getExcerpt(), cafe.getExcerptEn()))")
    @Mapping(target = "address", expression = "java(helper.getTranslated(language, "
            + "cafe.getAddress(), cafe.getAddressEn()))")
    @Mapping(target = "hours", expression = "java(helper.getTranslated(language, "
            + "cafe.getHours(), cafe.getHoursEn()))")
    @Mapping(target = "tags", source = "tags")
    @Mapping(target = "images", source = "images")
    CafeResponseDto toCafeResponseDto(Cafe cafe, @Context String language,
                                      @Context TranslationHelper helper);

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
    void updateCafeFromDto(CafeUpdateRequestDto dto, @MappingTarget Cafe cafe,
                           @Context CafeMapperHelper helper);

    @AfterMapping
    default void afterUpdateCafeFromDto(CafeUpdateRequestDto dto,
                                        @MappingTarget Cafe cafe,
                                        @Context CafeMapperHelper helper) {
        helper.mapTagsAndImages(dto, cafe);
    }
}
