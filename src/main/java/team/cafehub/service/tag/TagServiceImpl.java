package team.cafehub.service.tag;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.cafehub.dto.tag.TagRequestDto;
import team.cafehub.dto.tag.TagResponseDto;
import team.cafehub.exception.EntityNotFoundException;
import team.cafehub.mapper.tag.TagMapper;
import team.cafehub.repository.tag.TagRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Override
    public TagResponseDto create(TagRequestDto requestDto) {
        var tag = tagMapper.toModel(requestDto);
        return tagMapper.toTagResponse(tagRepository.save(tag));
    }

    @Transactional(readOnly = true)
    @Override
    public List<TagResponseDto> findAll() {
        return tagRepository.findAll().stream().map(tagMapper::toTagResponse).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public TagResponseDto findById(Long id) {
        var tag = tagRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find tag with id " + id));
        return tagMapper.toTagResponse(tag);
    }
}
