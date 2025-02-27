package medical.app.repository.search;

import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import java.time.LocalDate;
import java.time.LocalDateTime;
import medical.app.model.Visit;
import medical.app.repository.search.specification.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class TimeSpecificationProvider implements SpecificationProvider<Visit> {
    private static final String DATE_FIELD_NAME = "startDateTime";
    private static final String DOCTOR_FIELD_NAME = "doctor";
    private static final String PATIENT_FIELD_NAME = "patient";
    private static final String FUNCTION = "DATE";
    private static final String KEY = "time";

    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public Specification<Visit> getSpecification(Object[] params) {
        return (root, query, criteriaBuilder) -> {
            Subquery<LocalDateTime> subquery = query.subquery(LocalDateTime.class);
            Root<Visit> subRoot = subquery.from(Visit.class);
            LocalDateTime currentDate = LocalDateTime.now();

            subquery.select(criteriaBuilder.max(subRoot.get("startDateTime")).as(LocalDateTime.class))
                    .where(
                            criteriaBuilder.equal(subRoot.get(DOCTOR_FIELD_NAME),
                                    root.get(DOCTOR_FIELD_NAME)),
                            criteriaBuilder.equal(subRoot.get(PATIENT_FIELD_NAME),
                                    root.get(PATIENT_FIELD_NAME)),
                            criteriaBuilder.lessThanOrEqualTo(
                                    criteriaBuilder.function(FUNCTION, LocalDate.class,
                                            subRoot.get(DATE_FIELD_NAME)),
                                    currentDate.toLocalDate())
                    );
            query.where(criteriaBuilder.equal(root.get(DATE_FIELD_NAME), subquery));
            return query.getRestriction();
        };
    }
}
