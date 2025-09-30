package team.cafehub.repository.blogpost;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import team.cafehub.model.blog_post.BlogPost;

public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {
    @EntityGraph(attributePaths = {"category", "tags", "images", "user"})
    Optional<BlogPost> findById(Long id);

    @EntityGraph(attributePaths = {"category", "tags", "images"})
    Page<BlogPost> findAll(Pageable pageable);

    @Modifying
    @Query("UPDATE BlogPost c SET c.views = c.views + 1 WHERE c.id = :id")
    void updateViews(@Param("id") Long id);
}
