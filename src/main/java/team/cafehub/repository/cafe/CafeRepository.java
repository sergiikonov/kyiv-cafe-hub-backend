package team.cafehub.repository.cafe;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import team.cafehub.model.cafe.Cafe;

public interface CafeRepository extends JpaRepository<Cafe, Long>, JpaSpecificationExecutor<Cafe> {
    @EntityGraph(attributePaths = {"tags", "images"})
    Optional<Cafe> findById(Long id);

    @EntityGraph(attributePaths = {"tags", "images"})
    Page<Cafe> findAll(Specification<Cafe> spec, Pageable pageable);

    @Modifying
    @Query("UPDATE Cafe c SET c.views = c.views + 1 WHERE c.id = :id")
    void updateViews(@Param("id") Long id);
}
