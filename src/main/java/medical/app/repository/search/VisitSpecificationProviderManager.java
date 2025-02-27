package medical.app.repository.search;

import java.util.List;
import lombok.RequiredArgsConstructor;
import medical.app.exception.SpecificationProviderException;
import medical.app.model.Visit;
import medical.app.repository.search.specification.SpecificationProvider;
import medical.app.repository.search.specification.SpecificationProviderManager;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class VisitSpecificationProviderManager implements SpecificationProviderManager<Visit> {
    private final List<SpecificationProvider<Visit>> specificationProviders;

    @Override
    public SpecificationProvider<Visit> getSpecificationProvider(String key) {
        return specificationProviders.stream()
                .filter(provider -> provider.getKey().equals(key))
                .findFirst()
                .orElseThrow(() -> new SpecificationProviderException(
                        "Can't find correct specification provider for key: " + key));
    }
}
