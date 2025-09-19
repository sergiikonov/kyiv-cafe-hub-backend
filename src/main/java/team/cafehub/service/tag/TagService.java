package team.cafehub.service.tag;

import team.cafehub.dto.tag.TagRequestDto;
import team.cafehub.dto.tag.TagResponseDto;
import java.util.List;

public interface TagService {
    TagResponseDto create(TagRequestDto requestDto);

    List<TagResponseDto> findAll();

    TagResponseDto findById(Long id);
}
