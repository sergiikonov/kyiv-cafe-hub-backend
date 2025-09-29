package team.cafehub.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
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

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<CafeResponseDto> getAll(Pageable pageable) {
        log.info(String.valueOf(cafeService.findAll(pageable)));
        return cafeService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Found the cafe"),
            @ApiResponse(responseCode = "404", description = "Cafe not found")
    })
    public CafeResponseDto getById(@PathVariable Long id) {
        log.info(String.valueOf(cafeService.findById(id)));
        return cafeService.findById(id);
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CafeResponseDto save(@RequestBody @Valid CafeRequestDto requestDto,
                                Authentication authentication) {
        var saved = cafeService.save(requestDto, authentication);
        log.info(String.valueOf(saved));
        return saved;
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public CafeResponseDto update(@PathVariable Long id,
                                  @RequestBody @Valid CafeUpdateRequestDto requestDto,
                                  Authentication authentication) {
        log.info(String.valueOf(cafeService.updateById(requestDto, id, authentication)));
        return cafeService.updateById(requestDto, id, authentication);
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        cafeService.deleteById(id);
    }

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
