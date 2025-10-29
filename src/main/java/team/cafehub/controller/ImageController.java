package team.cafehub.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import team.cafehub.dto.image.ImageRequestDto;
import team.cafehub.dto.image.ImageResponseDto;
import team.cafehub.service.image.ImageService;

@Tag(name = "Image Controller",
        description = "Controller created for add & show images")
@RestController
@Slf4j
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @Operation(summary = "Add a new image",
            description = "This endpoint adds a new image and is accessible "
                    + "only by users with ADMINISTRATOR role.")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ImageResponseDto addImage(@Valid @RequestBody ImageRequestDto requestDto) {
        log.info(requestDto.imageUrl());
        return imageService.addImage(requestDto);
    }

    @Operation(summary = "Get image by id",
            description = "This endpoint get's image by id"
                    + "only by users with ADMINISTRATOR role.")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Found the image"),
            @ApiResponse(responseCode = "404", description = "Image not found")
    })
    @GetMapping("/{id}")
    public ImageResponseDto getImageById(@PathVariable Long id) {
        var image = imageService.findImageById(id);
        log.info(image.imageUrl());
        return image;
    }

    @Operation(summary = "Get all images",
            description = "This endpoint get's all images"
                    + "only by users with ADMINISTRATOR role.")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Found the image"),
            @ApiResponse(responseCode = "404", description = "Image not found")
    })
    @GetMapping
    public ResponseEntity<Page<ImageResponseDto>> getAll(
            @PageableDefault(size = 20, sort = "id",
                    direction = Sort.Direction.ASC) Pageable pageable) {
        var images = imageService.findAllImage(pageable);
        log.info("Image size {} ", images.getSize());
        return ResponseEntity.ok(images);
    }
}
