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
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import team.cafehub.dto.cafe.CafeRequestDto;
import team.cafehub.dto.cafe.CafeResponseDto;
import team.cafehub.dto.cafe.CafeSearchParameters;
import team.cafehub.dto.cafe.CafeUpdateRequestDto;
import team.cafehub.service.cafe.CafeService;

@Tag(name = "Cafe controller",
        description = "Controller which represents CRUD for Cafes + ability to search with params")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/cafe")
public class CafeController {
    private final CafeService cafeService;

    @Operation(summary = "Get all cafes",
            description = "This endpoint retrieves a paginated list of cafes sorted by name.")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public ResponseEntity<Page<CafeResponseDto>> getAll(
            @PageableDefault(size = 20, sort = "name",
                    direction = Sort.Direction.ASC) Pageable pageable) {
        var cafes = cafeService.findAll(pageable);
        log.info("Cafe size {} ", cafes.getSize());
        return ResponseEntity.ok(cafes);
    }

    @Operation(summary = "Get cafe by ID",
            description = "This endpoint returns a single cafe by its unique ID.")
    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Found the cafe"),
            @ApiResponse(responseCode = "404", description = "Cafe not found")
    })
    public ResponseEntity<CafeResponseDto> getById(@PathVariable Long id) {
        try {
            var cafe = cafeService.findById(id);
            log.info(cafe.name());
            System.out.println(cafe.name());
            return ResponseEntity.ok(cafe);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Create a new cafe",
            description = "This endpoint creates a new cafe and is accessible only "
                    + "by users with the ADMINISTRATOR role.")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<CafeResponseDto> save(@RequestBody @Valid
                                                    CafeRequestDto requestDto,
                                Authentication authentication) {
        var saved = cafeService.save(requestDto, authentication);
        log.info(saved.name());
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @Operation(summary = "Update a cafe by ID",
            description = "This endpoint updates an existing cafe by its ID and is "
                    + "accessible only by users with the ADMINISTRATOR role.")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public ResponseEntity<CafeResponseDto> update(@PathVariable Long id,
                                                  @RequestBody @Valid CafeUpdateRequestDto
                                                          requestDto,
                                                  Authentication authentication) {
        log.info(requestDto.name());
        var updatedCafe = cafeService.updateById(requestDto, id, authentication);
        log.info(updatedCafe.name());
        return ResponseEntity.ok(updatedCafe);
    }

    @Operation(summary = "Delete a cafe by ID",
            description = "This endpoint deletes a cafe by its ID and is "
                    + "accessible only by users with the ADMINISTRATOR role.")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cafeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Search cafes",
            description = "This endpoint searches for cafes by tags and/or name with pagination.")
    @GetMapping("/search")
    public ResponseEntity<Page<CafeResponseDto>> searchCafes(
            @RequestParam(required = false) String[] tags,
            @RequestParam(required = false) String name,
            @PageableDefault(size = 20, sort = "name") Pageable pageable) {
        Page<CafeResponseDto> result = cafeService
                .searchCafes(new CafeSearchParameters(tags, name), pageable);
        return ResponseEntity.ok(result);
    }
}
