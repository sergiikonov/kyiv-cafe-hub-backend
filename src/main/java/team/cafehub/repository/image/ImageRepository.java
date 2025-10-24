package team.cafehub.repository.image;

import org.springframework.data.jpa.repository.JpaRepository;
import team.cafehub.model.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
