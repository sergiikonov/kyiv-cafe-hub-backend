package team.cafehub.repository.cafe;

import org.springframework.data.jpa.repository.JpaRepository;
import team.cafehub.model.cafe.Cafe;

public interface CafeRepository extends JpaRepository<Cafe, Long> {
}
