package team.cafehub.service.blogpost;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import team.cafehub.dto.blogpost.BlogPostRequestDto;
import team.cafehub.dto.blogpost.BlogPostResponseDto;
import team.cafehub.dto.blogpost.BlogPostUpdateRequestDto;

public interface BlogPostService {
    BlogPostResponseDto save(BlogPostRequestDto requestDto, Authentication authentication);

    Page<BlogPostResponseDto> findAll(Pageable pageable, String language);

    BlogPostResponseDto findById(Long id, String language);

    BlogPostResponseDto updateById(BlogPostUpdateRequestDto requestDto, Long id,
                                   Authentication authentication, String language);

    void deleteById(Long id);
}
