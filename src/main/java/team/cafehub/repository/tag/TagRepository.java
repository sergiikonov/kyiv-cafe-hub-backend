package team.cafehub.repository.tag;

import org.springframework.data.jpa.repository.JpaRepository;
import team.cafehub.model.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
