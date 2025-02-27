package medical.app.repository.search.specification;

import medical.app.dto.SearchParameters;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationBuilder<T> {
    Specification<T> build(SearchParameters searchParameters);
}
