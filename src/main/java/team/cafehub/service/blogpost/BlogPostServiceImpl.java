package team.cafehub.service.blogpost;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.cafehub.dto.blogpost.BlogPostRequestDto;
import team.cafehub.dto.blogpost.BlogPostResponseDto;
import team.cafehub.exception.EntityNotFoundException;
import team.cafehub.mapper.blogpost.BlogPostMapper;
import team.cafehub.mapper.blogpost.BlogPostMapperHelper;
import team.cafehub.model.user.User;
import team.cafehub.repository.blogpost.BlogPostRepository;
import team.cafehub.repository.image.ImageRepository;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Transactional
public class BlogPostServiceImpl implements BlogPostService {
    private final BlogPostRepository blogPostRepository;
    private final BlogPostMapperHelper blogPostMapperHelper;
    private final BlogPostMapper blogPostMapper;
    private final ImageRepository imageRepository;

    @Override
    public BlogPostResponseDto save(BlogPostRequestDto requestDto, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        var blogPost = blogPostMapper.blogPostToModel(requestDto, blogPostMapperHelper);
        blogPost.setUser(user);
        blogPost.setViews(0);
        blogPost.getImages().forEach(image -> image.setBlogPost(blogPost));
        imageRepository.saveAll(blogPost.getImages());
        return blogPostMapper.toBlogPostResponseDto(blogPostRepository.save(blogPost));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<BlogPostResponseDto> findAll(Pageable pageable) {
        return blogPostRepository.findAll(pageable).map(blogPostMapper::toBlogPostResponseDto);
    }

    @Override
    public BlogPostResponseDto findById(Long id) {
        var blogPost = blogPostRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find BlogPost with id: " + id)
        );
        blogPostRepository.updateViews(id);
        return blogPostMapper.toBlogPostResponseDto(blogPost);
    }

    @Override
    public BlogPostResponseDto updateById(BlogPostRequestDto requestDto, Long id,
                                          Authentication authentication) {
        var blogPostToUpdate = blogPostRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find BlogPost with id: " + id)
        );
        blogPostToUpdate.setUser((User) authentication.getPrincipal());
        blogPostMapper.updateBlogPostFromDto(requestDto, blogPostToUpdate, blogPostMapperHelper);
        blogPostToUpdate.setUpdated(new Date());

        var updatedBlogPost = blogPostRepository.save(blogPostToUpdate);
        return blogPostMapper.toBlogPostResponseDto(updatedBlogPost);
    }

    @Override
    public void deleteById(Long id) {
        var blogPost = blogPostRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find blog post by id: " + id)
        );
        blogPostRepository.delete(blogPost);
    }
}
