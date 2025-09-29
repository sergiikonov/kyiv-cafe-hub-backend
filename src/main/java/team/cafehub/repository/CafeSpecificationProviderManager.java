package team.cafehub.repository;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import team.cafehub.exception.EntityNotFoundException;
import team.cafehub.model.cafe.Cafe;

@Component
@RequiredArgsConstructor
public class CafeSpecificationProviderManager implements SpecificationProviderManager<Cafe> {
    private final List<SpecificationProvider<Cafe>> cafeSpecificationProviders;

    @Override
    public SpecificationProvider<Cafe> getSpecificationProvider(String key) {
        return cafeSpecificationProviders.stream()
                .filter(p -> p.getKey().equals(key))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Can't find provider with key: " + key));
    }
}
