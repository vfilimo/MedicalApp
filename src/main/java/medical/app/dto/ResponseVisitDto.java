package medical.app.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class ResponseVisitDto {
    private String firstName;
    private String lastName;
    private VisitDto lastVisit;
}
