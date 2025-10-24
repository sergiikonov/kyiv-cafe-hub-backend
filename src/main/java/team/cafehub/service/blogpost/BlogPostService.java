package team.cafehub.service.blogpost;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import team.cafehub.dto.blogpost.BlogPostRequestDto;
import team.cafehub.dto.blogpost.BlogPostResponseDto;

public interface BlogPostService {
    BlogPostResponseDto save(BlogPostRequestDto requestDto, Authentication authentication);

    Page<BlogPostResponseDto> findAll(Pageable pageable);

    BlogPostResponseDto findById(Long id);

    BlogPostResponseDto updateById(BlogPostRequestDto requestDto, Long id,
                                   Authentication authentication);

    void deleteById(Long id);
}
