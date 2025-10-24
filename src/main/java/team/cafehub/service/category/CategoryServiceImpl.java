package team.cafehub.service.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.cafehub.dto.category.CategoryRequestDto;
import team.cafehub.dto.category.CategoryResponseDto;
import team.cafehub.exception.EntityNotFoundException;
import team.cafehub.mapper.category.CategoryMapper;
import team.cafehub.repository.category.CategoryRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResponseDto create(CategoryRequestDto requestDto) {
        return categoryMapper.toResponse(
                categoryRepository.save(categoryMapper.toModel(requestDto))
        );
    }

    @Transactional(readOnly = true)
    @Override
    public CategoryResponseDto findById(Long id) {
        var category = categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find category with id: " + id)
        );
        return categoryMapper.toResponse(category);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
