package team.cafehub.service.cafe;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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
import team.cafehub.repository.cafe.CafeRepository;
import team.cafehub.repository.cafe.CafeSpecificationBuilder;
import team.cafehub.util.TranslationHelper;

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
    private final TranslationHelper translationHelper;

    @Override
    @CachePut(value = "cafe", key = "#result.id + '-uk'")
    public CafeResponseDto save(CafeRequestDto requestDto, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        var cafe = cafeMapper.cafeToModel(requestDto, cafeMapperHelper);
        cafe.setUser(user);
        cafe.setViews(0);
        var savedCafe = cafeRepository.save(cafe);
        return cafeMapper.toCafeResponseDto(savedCafe, "uk", translationHelper);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CafeResponseDto> findAll(Pageable pageable, String language) {
        return cafeRepository.findAll(pageable).map(cafe ->
                cafeMapper.toCafeResponseDto(cafe, language, translationHelper));
    }

    @Override
    @Transactional
    public CafeResponseDto findById(Long id, String language) {
        cafeRepository.updateViews(id);
        return selfProxy.getCafe(id, language);
    }

    @Cacheable(value = "cafe", key = "#id + '-' + #language")
    @Transactional(readOnly = true)
    public CafeResponseDto getCafe(Long id, String language) {
        var cafe = cafeRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find cafe by id: " + id)
        );
        return cafeMapper.toCafeResponseDto(cafe, language, translationHelper);
    }

    @Override
    @CachePut(value = "cafe", key = "#id + '-' + #language")
    public CafeResponseDto updateById(CafeUpdateRequestDto requestDto, Long id,
                                      Authentication authentication,
                                      String language) {
        var cafeToUpdate = cafeRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find cafe by id: " + id)
        );
        User user = (User) authentication.getPrincipal();
        cafeMapper.updateCafeFromDto(requestDto, cafeToUpdate, cafeMapperHelper);
        cafeToUpdate.setUser(user);
        var savedCafe = cafeRepository.save(cafeToUpdate);
        return cafeMapper.toCafeResponseDto(savedCafe, language, translationHelper);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "cafe", key = "#id + '-uk'"),
            @CacheEvict(value = "cafe", key = "#id + '-en'")
    })
    public void deleteById(Long id) {
        cafeRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CafeResponseDto> searchCafes(CafeSearchParameters searchParameters,
                                             Pageable pageable, String language) {
        Specification<Cafe> spec = cafeSpecificationBuilder.build(searchParameters);
        Page<Cafe> cafes = cafeRepository.findAll(spec, pageable);
        return cafes.map(cafe ->
                cafeMapper.toCafeResponseDto(cafe, language, translationHelper)
        );
    }
}
