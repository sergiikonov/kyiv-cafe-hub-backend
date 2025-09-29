package team.cafehub.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Tag(name = "Blog Posts Controller",
    description = "This controller represents CRUD for blog posts")
@RestController
@RequiredArgsConstructor
@RequestMapping("/blog")
@Slf4j
public class BlogPostController {
    private final BlogPostService blogPostService;

    @Operation(summary = "Create a new blog post",
            description = "This endpoint creates a new blog post and is accessible " +
                    "only by users with the ADMINISTRATOR role.")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PostMapping
    public ResponseEntity<BlogPostResponseDto> createBlogPost(@Valid @RequestBody BlogPostRequestDto requestDto,
                                                              Authentication authentication) {
        log.info(requestDto.content());
        return ResponseEntity.status(HttpStatus.CREATED).body(blogPostService.save(requestDto, authentication));
    }

    @Operation(summary = "Get all blog posts",
            description = "This endpoint returns a paginated list of " +
                    "blog posts sorted by creation date in descending order.")
    @GetMapping
    public ResponseEntity<Page<BlogPostResponseDto>> getAllBlogPosts(
            @PageableDefault(size = 20, sort = "created", direction = Sort.Direction.DESC) Pageable pageable) {
        log.info(blogPostService.findAll(pageable).toString());
        return ResponseEntity.ok(blogPostService.findAll(pageable));
    }

    @Operation(summary = "Get a blog post by ID",
            description = "This endpoint returns a single blog post by its unique ID.")
    @GetMapping("/{id}")
    public ResponseEntity<BlogPostResponseDto> getBlogPostById(@PathVariable Long id) {
        log.info(blogPostService.findById(id).content());
        return ResponseEntity.ok(blogPostService.findById(id));
    }

    @Operation(summary = "Update a blog post by ID",
            description = "This endpoint updates an existing blog post by its ID and is " +
                    "accessible only by users with the ADMINISTRATOR role.")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PutMapping("/{id}")
    public ResponseEntity<BlogPostResponseDto> updateBlogPost(
            @PathVariable Long id,
            @Valid @RequestBody BlogPostRequestDto requestDto, Authentication authentication) {
        log.info(requestDto.content());
        return ResponseEntity.ok(blogPostService.updateById(requestDto, id, authentication));
    }

    @Operation(summary = "Delete a blog post by ID",
            description = "This endpoint deletes a blog post by its ID and is " +
                    "accessible only by users with the ADMINISTRATOR role.")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlogPost(@PathVariable Long id) {
        blogPostService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
