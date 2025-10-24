package team.cafehub.repository.cafe;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import team.cafehub.model.cafe.Cafe;
import team.cafehub.repository.SpecificationProvider;

@Component
public class NameSpecificationProvider implements SpecificationProvider<Cafe> {
    @Override
    public String getKey() {
        return "name";
    }

    @Override
    public Specification<Cafe> getSpecification(String[] params) {
        return (root, query, criteriaBuilder) -> {
            if (params == null || params.length == 0 || params[0] == null
                    || params[0].trim().isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            String searchName = params[0].toLowerCase().trim();
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("name")),
                    "%" + searchName + "%"
            );
        };
    }
}
