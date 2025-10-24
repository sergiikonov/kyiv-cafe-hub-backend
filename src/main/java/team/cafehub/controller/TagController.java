package team.cafehub.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import team.cafehub.dto.tag.TagRequestDto;
import team.cafehub.dto.tag.TagResponseDto;
import team.cafehub.service.tag.TagService;

@Tag(name = "Tag Controller",
        description = "Controller which represented for create and take tags")
@RestController
@Slf4j
@RequestMapping("/tag")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    @Operation(summary = "Get all tags",
            description = "This endpoint returns a list of all tags. "
                    + "Accessible only by users with ADMINISTRATOR role.")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public ResponseEntity<List<TagResponseDto>> getAllTags() {
        var tags = tagService.findAll();
        log.info("Found {} tags", tags.size());
        return ResponseEntity.ok(tags);
    }

    @Operation(summary = "Create a new tag",
            description = "This endpoint creates a new tag and is "
                    + "accessible only by users with ADMINISTRATOR role.")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<TagResponseDto> createTag(@Valid @RequestBody
                                                        TagRequestDto requestDto) {
        var created = tagService.create(requestDto);
        log.info(created.name());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Get tag by ID",
            description = "This endpoint returns a single tag by its unique ID. "
                    + "Returns 404 if the tag is not found.")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Found the tag"),
            @ApiResponse(responseCode = "404", description = "Tag not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TagResponseDto> getTagById(@PathVariable Long id) {
        try {
            var tag = tagService.findById(id);
            log.info("Found tag with id {}: {}", id, tag.name());
            return ResponseEntity.ok(tag);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
