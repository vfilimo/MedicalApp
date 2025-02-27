package medical.app.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import medical.app.model.Visit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VisitRepository extends JpaRepository<Visit, Long>, JpaSpecificationExecutor<Visit> {


    @Query("SELECT v FROM Visit v "
            + "JOIN FETCH v.doctor JOIN FETCH v.patient "
            + "WHERE v.doctor.id = :doctorId AND v.endDateTime < :endDateTime")
    Optional<Visit> validateVisit(@Param("doctorId") Integer doctorId,
                                  @Param("endDateTime") LocalDateTime endDateTime);

    @EntityGraph(attributePaths = {"doctor", "patient"})
    @Override
    Page<Visit> findAll(Specification<Visit> visitSpecification, Pageable pageable);

    @Query("SELECT v FROM Visit v "
            + "JOIN FETCH v.doctor JOIN FETCH v.patient "
            + "WHERE v.doctor.id =:doctorId "
                + "AND((:startDateTime BETWEEN v.startDateTime AND v.endDateTime) "
                    + "OR (:endDateTime BETWEEN v.startDateTime AND v.endDateTime) OR "
                        + "(v.startDateTime BETWEEN :startDateTime AND :endDateTime)) "
            + "ORDER BY v.startDateTime ASC"
    )
    Optional<Visit> findNextVisitForDoctor(
            @Param("doctorId") Integer doctorId,
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime
    );
}
