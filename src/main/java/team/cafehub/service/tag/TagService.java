package team.cafehub.service.tag;

import team.cafehub.dto.tag.TagResponseDto;

public interface TagService {
    TagResponseDto create(TagRequestDto requestDto);
}
