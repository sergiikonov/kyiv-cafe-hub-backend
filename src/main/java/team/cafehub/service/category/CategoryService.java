package team.cafehub.service.category;

import team.cafehub.dto.category.CategoryRequestDto;
import team.cafehub.dto.category.CategoryResponseDto;

public interface CategoryService {
    CategoryResponseDto create(CategoryRequestDto requestDto);

    CategoryResponseDto findById(Long id);

    void deleteById(Long id);
}
