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
import team.cafehub.util.TranslationHelper;

@Service
@Transactional
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;
    private final TranslationHelper translationHelper;

    @Override
    public TagResponseDto create(TagRequestDto requestDto) {
        var tag = tagMapper.toModel(requestDto);
        var savedTag = tagRepository.save(tag);
        return tagMapper.toTagResponseDto(savedTag, "uk", translationHelper);
    }

    @Transactional(readOnly = true)
    @Override
    public List<TagResponseDto> findAll(String language) {
        return tagRepository.findAll().stream()
                .map(tag -> tagMapper.toTagResponseDto(tag, language, translationHelper))
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public TagResponseDto findById(Long id, String language) {
        var tag = tagRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find tag with id " + id));
        return tagMapper.toTagResponseDto(tag, language, translationHelper);
    }
}
