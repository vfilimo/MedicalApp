package medical.app.repository.search;

import medical.app.exception.ValidationException;
import medical.app.model.Visit;
import medical.app.repository.search.specification.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class SearchSpecificationProvider implements SpecificationProvider<Visit> {
    private static final String FIELD_NAME = "patient";
    private static final String LAST_NAME = "lastName";
    private static final String FIRST_NAME = "firstName";
    private static final String KEY = "search";

    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public Specification<Visit> getSpecification(Object[] params) {
        String search = (String) params[0];
        String[] split = search.split(" ");

        if (split.length == 2) {
            return createSpecificationForName(split[0], split[1], true);
        } else if (split.length == 1) {
            return createSpecificationForName(split[0], split[0], false);
        } else {
            throw new ValidationException("Invalid search parameters: " + search);
        }
    }

    private Specification<Visit> createSpecificationForName(String firstName, String lastName, boolean isAnd) {
        Specification<Visit> firstNameSpecification = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(FIELD_NAME).get(FIRST_NAME), firstName);
        Specification<Visit> lastNameSpecification = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(FIELD_NAME).get(LAST_NAME), lastName);

        return isAnd ? firstNameSpecification.and(lastNameSpecification)
                : firstNameSpecification.or(lastNameSpecification);
    }
}
