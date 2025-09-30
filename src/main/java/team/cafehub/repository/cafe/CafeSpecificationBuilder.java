package team.cafehub.repository.cafe;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import team.cafehub.dto.cafe.CafeSearchParameters;
import team.cafehub.model.cafe.Cafe;
import team.cafehub.repository.SpecificationBuilder;
import team.cafehub.repository.SpecificationProviderManager;

@Component
@RequiredArgsConstructor
public class CafeSpecificationBuilder implements SpecificationBuilder<Cafe> {
    private final SpecificationProviderManager<Cafe> providerManager;

    @Override
    public Specification<Cafe> build(CafeSearchParameters searchParameters) {
        Specification<Cafe> spec = Specification.where(null);
        if (searchParameters.tags() != null && searchParameters.tags().length > 0) {
            spec = spec.and(providerManager
                    .getSpecificationProvider("tag")
                    .getSpecification(searchParameters.tags()));
        }

        if (searchParameters.name() != null && !searchParameters.name().trim().isEmpty()) {
            spec = spec.and(providerManager
                    .getSpecificationProvider("name")
                    .getSpecification(new String[]{searchParameters.name()}));
        }

        return spec;
    }
}
