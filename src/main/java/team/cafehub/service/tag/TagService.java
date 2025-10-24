package team.cafehub.service.tag;

import java.util.List;
import team.cafehub.dto.tag.TagRequestDto;
import team.cafehub.dto.tag.TagResponseDto;

public interface TagService {
    TagResponseDto create(TagRequestDto requestDto);

    List<TagResponseDto> findAll();

    TagResponseDto findById(Long id);
}
