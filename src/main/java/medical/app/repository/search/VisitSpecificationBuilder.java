package medical.app.repository.search;

import lombok.RequiredArgsConstructor;
import medical.app.dto.SearchParameters;
import medical.app.model.Visit;
import medical.app.repository.search.specification.SpecificationProviderManager;
import medical.app.repository.search.specification.SpecificationBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class VisitSpecificationBuilder implements SpecificationBuilder<Visit> {
    private final SpecificationProviderManager<Visit> specificationProviderManager;

    @Override
    public Specification<Visit> build(SearchParameters searchParameters) {
        Specification<Visit> specification = Specification.allOf();
        if (searchParameters.doctorsId() != null && searchParameters.doctorsId().length > 0) {
            specification = specification.and(
                    specificationProviderManager.getSpecificationProvider("doctor_id")
                    .getSpecification(searchParameters.doctorsId()));
        }
        if (searchParameters.search() != null && searchParameters.search().length() > 0) {
            specification = specification.and(
                    specificationProviderManager.getSpecificationProvider("search")
                    .getSpecification(new String[]{searchParameters.search()}));
        }
        specification = specification.and(specificationProviderManager
                .getSpecificationProvider("time")
                .getSpecification(new Object[0]));
        return specification;
    }
}
