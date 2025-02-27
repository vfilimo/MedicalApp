package medical.app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VisitDto {
    private String start;
    private String end;
    DoctorDto doctor;
}
