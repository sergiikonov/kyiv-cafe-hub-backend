package team.cafehub.service.cafe;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.cafehub.dto.cafe.CafeRequestDto;
import team.cafehub.dto.cafe.CafeResponseDto;
import team.cafehub.dto.cafe.CafeSearchParameters;
import team.cafehub.dto.cafe.CafeUpdateRequestDto;
import team.cafehub.exception.EntityNotFoundException;
import team.cafehub.mapper.cafe.CafeMapper;
import team.cafehub.mapper.cafe.CafeMapperHelper;
import team.cafehub.model.cafe.Cafe;
import team.cafehub.model.user.User;
import team.cafehub.repository.CafeSpecificationBuilder;
import team.cafehub.repository.cafe.CafeRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class CafeServiceImpl implements CafeService {
    private final CafeRepository cafeRepository;
    private final CafeMapper cafeMapper;
    private final CafeMapperHelper cafeMapperHelper;
    private final CafeSpecificationBuilder cafeSpecificationBuilder;

    @Override
    public CafeResponseDto save(CafeRequestDto requestDto, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        var cafe = cafeMapper.cafeToModel(requestDto, cafeMapperHelper);
        cafe.setUser(user);
        cafe.setViews(0);
        return cafeMapper.toCafeResponseDto(cafeRepository.save(cafe));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CafeResponseDto> findAll(Pageable pageable) {
        return cafeRepository.findAll(pageable).map(cafeMapper::toCafeResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public CafeResponseDto findById(Long id) {
        var cafe = cafeRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find cafe by id: " + id)
        );
        cafe.setViews(cafe.getViews() + 1);
        return cafeMapper.toCafeResponseDto(cafe);
    }

    @Override
    public CafeResponseDto updateById(CafeUpdateRequestDto requestDto, Long id,
                                      Authentication authentication) {
        var cafeToUpdate = cafeRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find cafe by id: " + id)
        );
        User user = (User) authentication.getPrincipal();
        cafeMapper.updateCafeFromDto(requestDto, cafeToUpdate);
        cafeToUpdate.setUser(user);
        return cafeMapper.toCafeResponseDto(cafeRepository.save(cafeToUpdate));
    }

    @Override
    public void deleteById(Long id) {
        cafeRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CafeResponseDto> searchCafes(CafeSearchParameters searchParameters, Pageable pageable) {
        Specification<Cafe> spec = cafeSpecificationBuilder.build(searchParameters);
        Page<Cafe> cafes = cafeRepository.findAll(spec, pageable);
        return cafes.map(cafeMapper::toCafeResponseDto);
    }
}
