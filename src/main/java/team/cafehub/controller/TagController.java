package team.cafehub.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import team.cafehub.dto.tag.TagRequestDto;
import team.cafehub.dto.tag.TagResponseDto;
import team.cafehub.service.tag.TagService;
import java.util.List;

@RestController
@RequestMapping("/tag")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<TagResponseDto> getAllTags() {
        return tagService.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public TagResponseDto createTag(@Valid @RequestBody TagRequestDto requestDto) {
        return tagService.create(requestDto);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Found the tag"),
            @ApiResponse(responseCode = "404", description = "Tag not found")
    })
    @GetMapping("/{id}")
    public TagResponseDto getTagById(@PathVariable Long id) {
        return tagService.findById(id);
    }
}
