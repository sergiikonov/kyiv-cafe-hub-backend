package team.cafehub.repository;

import org.springframework.data.jpa.domain.Specification;
import team.cafehub.dto.cafe.CafeSearchParameters;

public interface SpecificationBuilder<T> {
    Specification<T> build(CafeSearchParameters searchParameters);
}
