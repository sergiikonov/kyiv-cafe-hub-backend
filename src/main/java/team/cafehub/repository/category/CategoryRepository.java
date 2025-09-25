package team.cafehub.repository.category;

import org.springframework.data.jpa.repository.JpaRepository;
import team.cafehub.model.blog_post.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
