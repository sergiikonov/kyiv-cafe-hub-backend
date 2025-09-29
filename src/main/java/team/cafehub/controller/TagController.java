package team.cafehub.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import team.cafehub.dto.tag.TagRequestDto;
import team.cafehub.dto.tag.TagResponseDto;
import team.cafehub.service.tag.TagService;
import java.util.List;

@Tag(name = "Tag Controller",
        description = "Controller which represented for create and take tags")
@RestController
@RequestMapping("/tag")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    @Operation(summary = "Get all tags",
            description = "This endpoint returns a list of all tags. " +
                    "Accessible only by users with ADMINISTRATOR role.")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<TagResponseDto> getAllTags() {
        return tagService.findAll();
    }

    @Operation(summary = "Create a new tag",
            description = "This endpoint creates a new tag and is accessible only by users with ADMINISTRATOR role.")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public TagResponseDto createTag(@Valid @RequestBody TagRequestDto requestDto) {
        return tagService.create(requestDto);
    }

    @Operation(summary = "Get tag by ID",
            description = "This endpoint returns a single tag by its unique ID. Returns 404 if the tag is not found.")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Found the tag"),
            @ApiResponse(responseCode = "404", description = "Tag not found")
    })
    @GetMapping("/{id}")
    public TagResponseDto getTagById(@PathVariable Long id) {
        return tagService.findById(id);
    }
}
