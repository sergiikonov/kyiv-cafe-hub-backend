package team.cafehub.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import team.cafehub.model.cafe.Cafe;

public interface CafeRepository extends JpaRepository<Cafe, Long> {
}
