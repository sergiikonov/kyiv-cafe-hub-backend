package team.cafehub.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import team.cafehub.config.MapStructConfig;
import team.cafehub.dto.cafe.CafeRequestDto;
import team.cafehub.dto.cafe.CafeResponseDto;
import team.cafehub.dto.cafe.CafeUpdateRequestDto;
import team.cafehub.model.cafe.Cafe;

@Mapper(componentModel = "spring", config = MapStructConfig.class)
public interface CafeMapper {
    CafeResponseDto toCafeResponseDto(Cafe cafe);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    Cafe cafeToModel(CafeRequestDto cafeRequestDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    void updateCafeFromDto(CafeUpdateRequestDto dto, @MappingTarget Cafe cafe);
}
