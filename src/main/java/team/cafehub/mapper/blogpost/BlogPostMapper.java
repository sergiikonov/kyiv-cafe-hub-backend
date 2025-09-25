package team.cafehub.mapper.blogpost;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import team.cafehub.config.MapStructConfig;
import team.cafehub.dto.blogpost.BlogPostRequestDto;
import team.cafehub.dto.blogpost.BlogPostResponseDto;
import team.cafehub.model.blog_post.BlogPost;

@Mapper(componentModel = "spring", config = MapStructConfig.class)
public interface BlogPostMapper {
    BlogPostResponseDto toBlogPostResponseDto(BlogPost blogPost);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "images", ignore = true)
    @Mapping(target = "user", ignore = true)
    BlogPost blogPostToModel(BlogPostRequestDto blogPostRequestDto,
                             @Context BlogPostMapperHelper helper);

    @AfterMapping
    default void afterBlogPostRequestDtoToBlogPost(BlogPostRequestDto dto,
                                                   @MappingTarget BlogPost blogPost,
                                                   @Context BlogPostMapperHelper helper) {
        helper.mapRelationships(dto, blogPost);
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "images", ignore = true)
    @Mapping(target = "user", ignore = true)
    void updateBlogPostFromDto(BlogPostRequestDto dto,
                               @MappingTarget BlogPost blogPost,
                               @Context BlogPostMapperHelper helper);
}
