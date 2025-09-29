package team.cafehub.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import team.cafehub.model.cafe.Cafe;

@Component
public class NameSpecificationProvider implements SpecificationProvider<Cafe> {
    @Override
    public String getKey() {
        return "name";
    }

    @Override
    public Specification<Cafe> getSpecification(String[] params) {
        return (root, query, criteriaBuilder) -> {
            if (params == null || params.length == 0 || params[0] == null || params[0].trim().isEmpty()) {
                return criteriaBuilder.conjunction(); // Повертаємо "завжди true" якщо параметр пустий
            }

            String searchName = params[0].toLowerCase().trim();
            // Шукаємо кав'ярні, де назва містить введений текст (case-insensitive)
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("name")),
                    "%" + searchName + "%"
            );
        };
    }
}
