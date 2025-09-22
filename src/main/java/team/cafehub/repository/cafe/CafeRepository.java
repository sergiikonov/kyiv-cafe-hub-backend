package team.cafehub.repository.cafe;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import team.cafehub.model.cafe.Cafe;

import java.util.Optional;

public interface CafeRepository extends JpaRepository<Cafe, Long> {
    @EntityGraph(attributePaths = {"tags", "images"})
    Optional<Cafe> findById(Long id);
}
