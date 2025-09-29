package team.cafehub.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import team.cafehub.dto.category.CategoryRequestDto;
import team.cafehub.dto.category.CategoryResponseDto;
import team.cafehub.service.category.CategoryService;

@Tag(name = "Category Controller",
        description = "Controller represents categories for blog-posts")
@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    @Operation(summary = "Get category by ID",
            description = "This endpoint returns a single category by its unique ID. " +
                    "Accessible only by users with ADMINISTRATOR role.")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.findById(id));
    }

    @Operation(summary = "Create a new category",
            description = "This endpoint creates a new category and is " +
                    "accessible only by users with ADMINISTRATOR role.")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PostMapping
    public ResponseEntity<CategoryResponseDto> create(@Valid @RequestBody
                                                          CategoryRequestDto requestDto) {
        return ResponseEntity.ok(categoryService.create(requestDto));
    }

    @Operation(summary = "Delete category by ID",
            description = "This endpoint deletes a category by its unique ID. " +
                    "Accessible only by users with ADMINISTRATOR role.")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        categoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
