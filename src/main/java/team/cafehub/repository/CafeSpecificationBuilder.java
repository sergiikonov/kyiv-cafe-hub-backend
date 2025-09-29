package team.cafehub.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import team.cafehub.dto.cafe.CafeSearchParameters;
import team.cafehub.model.cafe.Cafe;

@Component
@RequiredArgsConstructor
public class CafeSpecificationBuilder implements SpecificationBuilder<Cafe> {
    private final SpecificationProviderManager<Cafe> providerManager;

    @Override
    public Specification<Cafe> build(CafeSearchParameters searchParameters) {
        Specification<Cafe> spec = Specification.unrestricted();
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
