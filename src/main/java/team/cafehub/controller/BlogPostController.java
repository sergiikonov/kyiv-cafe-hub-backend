package team.cafehub.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import team.cafehub.dto.blogpost.BlogPostRequestDto;
import team.cafehub.dto.blogpost.BlogPostResponseDto;
import team.cafehub.service.blogpost.BlogPostService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/blog")
public class BlogPostController {
    private final BlogPostService blogPostService;

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PostMapping
    public ResponseEntity<BlogPostResponseDto> createBlogPost(@Valid @RequestBody BlogPostRequestDto requestDto,
                                                              Authentication authentication) {
        return ResponseEntity.status(HttpStatus.CREATED).body(blogPostService.save(requestDto, authentication));
    }

    @GetMapping
    public ResponseEntity<Page<BlogPostResponseDto>> getAllBlogPosts(
            @PageableDefault(size = 20, sort = "created", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(blogPostService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogPostResponseDto> getBlogPostById(@PathVariable Long id) {
        return ResponseEntity.ok(blogPostService.findById(id));
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PutMapping("/{id}")
    public ResponseEntity<BlogPostResponseDto> updateBlogPost(
            @PathVariable Long id,
            @Valid @RequestBody BlogPostRequestDto requestDto, Authentication authentication) {
        return ResponseEntity.ok(blogPostService.updateById(requestDto, id, authentication));
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlogPost(@PathVariable Long id) {
        blogPostService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
