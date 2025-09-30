package team.cafehub.repository.cafe;

import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import team.cafehub.model.Tag;
import team.cafehub.model.cafe.Cafe;
import team.cafehub.repository.SpecificationProvider;

@Component
public class TagSpecificationProvider implements SpecificationProvider<Cafe> {
    @Override
    public String getKey() {
        return "tag";
    }

    @Override
    public Specification<Cafe> getSpecification(String[] params) {
        return (root, query, criteriaBuilder) -> {
            Join<Cafe, Tag> tags = root.join("tags");
            return tags.get("slug").in((Object[]) params); // Фільтрація по тегу (або будь-яке інше поле)
        };
    }
}
