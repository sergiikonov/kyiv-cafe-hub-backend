package team.cafehub.service.cafe;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
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
import team.cafehub.repository.cafe.CafeSpecificationBuilder;
import team.cafehub.repository.cafe.CafeRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class CafeServiceImpl implements CafeService {
    @Autowired
    @Lazy
    private CafeServiceImpl selfProxy;

    private final CafeRepository cafeRepository;
    private final CafeMapper cafeMapper;
    private final CafeMapperHelper cafeMapperHelper;
    private final CafeSpecificationBuilder cafeSpecificationBuilder;

    @Override
    @CachePut(value = "cafe", key = "#result.id")
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
    @Transactional
    public CafeResponseDto findById(Long id) {
        cafeRepository.updateViews(id);
        return selfProxy.getCafe(id);
    }

    @Cacheable(value = "cafe", key = "#id")
    @Transactional(readOnly = true)
    public CafeResponseDto getCafe(Long id) {
        var cafe = cafeRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find cafe by id: " + id)
        );
        return cafeMapper.toCafeResponseDto(cafe);
    }

    @Override
    @CachePut(value = "cafe", key = "#id")
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
    @CacheEvict(value = "cafe", key = "#id")
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
