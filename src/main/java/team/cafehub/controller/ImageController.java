package team.cafehub.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import team.cafehub.dto.image.ImageRequestDto;
import team.cafehub.dto.image.ImageResponseDto;
import team.cafehub.service.image.ImageService;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ImageResponseDto addImage(@Valid @RequestBody ImageRequestDto requestDto) {
        return imageService.addImage(requestDto);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Found the image"),
            @ApiResponse(responseCode = "404", description = "Image not found")
    })
    @GetMapping("/{id}")
    public ImageResponseDto getImageById(@PathVariable Long id) {
        return imageService.findImageById(id);
    }
}
