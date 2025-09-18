package team.cafehub.service.cafe;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.cafehub.dto.cafe.CafeRequestDto;
import team.cafehub.dto.cafe.CafeResponseDto;
import team.cafehub.dto.cafe.CafeUpdateRequestDto;
import team.cafehub.exception.EntityNotFoundException;
import team.cafehub.mapper.CafeMapper;
import team.cafehub.repository.user.CafeRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class CafeServiceImpl implements CafeService {
    private final CafeRepository cafeRepository;
    private final CafeMapper mapper;

    @Override
    public CafeResponseDto save(CafeRequestDto requestDto) {
        var cafe = mapper.cafeToModel(requestDto);
        return mapper.toCafeResponseDto(cafeRepository.save(cafe));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CafeResponseDto> findAll(Pageable pageable) {
        return cafeRepository.findAll(pageable).map(mapper::toCafeResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public CafeResponseDto findById(Long id) {
        return mapper.toCafeResponseDto(cafeRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find cafe by id: " + id)
        ));
    }

    @Override
    public CafeResponseDto updateById(CafeUpdateRequestDto requestDto, Long id) {
        var cafeToUpdate = cafeRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find cafe by id: " + id)
        );
        mapper.updateCafeFromDto(requestDto, cafeToUpdate);
        return mapper.toCafeResponseDto(cafeRepository.save(cafeToUpdate));
    }

    @Override
    public void deleteById(Long id) {
        cafeRepository.deleteById(id);
    }
}
