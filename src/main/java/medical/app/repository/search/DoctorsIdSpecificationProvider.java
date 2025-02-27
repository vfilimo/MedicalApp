package medical.app.repository.search;

import medical.app.model.Visit;
import medical.app.repository.search.specification.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class DoctorsIdSpecificationProvider
        implements SpecificationProvider<Visit> {
    private static final String FIELD_NAME = "doctor";
    private static final String FIELD_ID = "id";
    private static final String KEY = "doctor_id";

    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public Specification<Visit> getSpecification(Object[] params) {
        return (root, query, criteriaBuilder) -> root.get(FIELD_NAME).get(FIELD_ID)
                .in(params);
    }
}
