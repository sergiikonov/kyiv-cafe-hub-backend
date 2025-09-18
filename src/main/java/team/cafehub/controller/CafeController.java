package team.cafehub.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import team.cafehub.dto.cafe.CafeRequestDto;
import team.cafehub.dto.cafe.CafeResponseDto;
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

//    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CafeResponseDto save(@RequestBody @Valid CafeRequestDto requestDto) {
        var saved = cafeService.save(requestDto);
        log.info(String.valueOf(saved));
        return saved;
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public CafeResponseDto update(@PathVariable Long id,
                                  @RequestBody @Valid CafeUpdateRequestDto requestDto) {
        log.info(String.valueOf(cafeService.updateById(requestDto, id)));
        return cafeService.updateById(requestDto, id);
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        cafeService.deleteById(id);
    }
}
