package team.cafehub.service.cafe;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import team.cafehub.dto.cafe.CafeRequestDto;
import team.cafehub.dto.cafe.CafeResponseDto;
import team.cafehub.dto.cafe.CafeUpdateRequestDto;

public interface CafeService {
    CafeResponseDto save(CafeRequestDto requestDto, Authentication authentication);

    Page<CafeResponseDto> findAll(Pageable pageable);

    CafeResponseDto findById(Long id);

    CafeResponseDto updateById(CafeUpdateRequestDto requestDto, Long id,
                               Authentication authentication);

    void deleteById(Long id);
}
