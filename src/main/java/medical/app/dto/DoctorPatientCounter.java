package medical.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DoctorPatientCounter {
    private Integer doctorId;
    private Long totalPatients;
}
