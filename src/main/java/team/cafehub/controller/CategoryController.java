package team.cafehub.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.cafehub.dto.category.CategoryRequestDto;
import team.cafehub.dto.category.CategoryResponseDto;
import team.cafehub.service.category.CategoryService;

@Tag(name = "Category Controller",
        description = "Controller represents categories for blog-posts")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    @Operation(summary = "Get category by ID",
            description = "This endpoint returns a single category by its unique ID. "
                    + "Accessible only by users with ADMINISTRATOR role.")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> getCategoryById(@PathVariable Long id) {
        var category = categoryService.findById(id);
        log.info(category.name());
        return ResponseEntity.ok(category);
    }

    @Operation(summary = "Create a new category",
            description = "This endpoint creates a new category and is "
                    + "accessible only by users with ADMINISTRATOR role.")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PostMapping
    public ResponseEntity<CategoryResponseDto> create(@Valid @RequestBody
                                                          CategoryRequestDto requestDto) {
        var saved = categoryService.create(requestDto);
        log.info(saved.name());
        return ResponseEntity.ok(saved);
    }

    @Operation(summary = "Delete category by ID",
            description = "This endpoint deletes a category by its unique ID. "
                    + "Accessible only by users with ADMINISTRATOR role.")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        categoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
