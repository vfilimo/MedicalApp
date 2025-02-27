package medical.app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorDto {
    private String firstName;
    private String lastName;
    private Long totalPatients;
}
