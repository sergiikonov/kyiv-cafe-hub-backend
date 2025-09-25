package team.cafehub.mapper.blogpost;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import team.cafehub.dto.blogpost.BlogPostRequestDto;
import team.cafehub.exception.EntityNotFoundException;
import team.cafehub.model.Image;
import team.cafehub.model.Tag;
import team.cafehub.model.blog_post.BlogPost;
import team.cafehub.model.blog_post.Category;
import team.cafehub.repository.category.CategoryRepository;
import team.cafehub.repository.image.ImageRepository;
import team.cafehub.repository.tag.TagRepository;

@Component
public class BlogPostMapperHelper {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ImageRepository imageRepository;

    public void mapRelationships(BlogPostRequestDto dto, BlogPost blogPost) {
        if (dto.categoryId() != null) {
            Set<Category> categories = new HashSet<>();
            for (Long categoryId : dto.categoryId()) {
                Category category = categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new EntityNotFoundException("Category not found with id: "
                                + categoryId));
                categories.add(category);
            }
            blogPost.setCategories(categories);
        }

        if (dto.tagIds() != null) {
            Set<Tag> tags = new HashSet<>(tagRepository.findAllById(dto.tagIds()));
            blogPost.setTags(tags);
        }

        if (dto.imageIds() != null && !dto.imageIds().isEmpty()) {
            List<Image> images = imageRepository.findAllById(dto.imageIds());
            blogPost.setImages(images);
        }
    }
}
