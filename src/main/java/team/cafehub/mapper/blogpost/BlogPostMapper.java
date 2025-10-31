package team.cafehub.mapper.blogpost;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import team.cafehub.config.MapStructConfig;
import team.cafehub.dto.blogpost.BlogPostRequestDto;
import team.cafehub.dto.blogpost.BlogPostResponseDto;
import team.cafehub.dto.blogpost.BlogPostUpdateRequestDto;
import team.cafehub.mapper.category.CategoryMapper;
import team.cafehub.mapper.tag.TagMapper;
import team.cafehub.model.blogpost.BlogPost;
import team.cafehub.util.TranslationHelper;

@Mapper(componentModel = "spring", config = MapStructConfig.class,
        uses = {
                TranslationHelper.class,
                TagMapper.class,
                CategoryMapper.class,
                BlogPostMapperHelper.class
        })
public interface BlogPostMapper {
    @Mapping(target = "title",
            expression = "java(helper.getTranslated(language, "
                    + "blogPost.getTitle(), blogPost.getTitleEn()))")
    @Mapping(target = "excerpt",
            expression = "java(helper.getTranslated(language, blogPost.getExcerpt(), "
                    + "blogPost.getExcerptEn()))")
    @Mapping(target = "content",
            expression = "java(helper.getTranslated(language, blogPost.getContent(), "
                    + "blogPost.getContentEn()))")
    @Mapping(target = "tags", source = "tags")
    @Mapping(target = "categories", source = "categories")
    BlogPostResponseDto toBlogPostResponseDto(BlogPost blogPost, @Context String language,
                                              @Context TranslationHelper helper);

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
    void updateBlogPostFromDto(BlogPostUpdateRequestDto dto,
                               @MappingTarget BlogPost blogPost,
                               @Context BlogPostMapperHelper helper);

    @AfterMapping
    default void afterBlogPostUpdateRequestDtoToBlogPost(BlogPostUpdateRequestDto dto,
                                                         @MappingTarget BlogPost blogPost,
                                                         @Context BlogPostMapperHelper helper) {

        helper.mapRelationships(dto, blogPost);
    }
}
