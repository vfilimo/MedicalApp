package medical.app.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import medical.app.dto.DoctorPatientCounter;
import medical.app.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    @Query("SELECT new medical.app.dto.DoctorPatientCounter(d.id, COUNT(DISTINCT v.patient.id)) "
            + "FROM Visit v "
            + "JOIN v.doctor d "
            + "WHERE d.id IN :doctorIds "
            + "GROUP BY d.id")
    List<DoctorPatientCounter> findDoctorsWithPatientCount(@Param("doctorIds") List<Integer> doctorIds);
}
