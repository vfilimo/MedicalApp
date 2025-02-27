package medical.app.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class CreateVisitDto {
    @NotNull
    @FutureOrPresent(message = "Invalid date: the date: 'start' must be today or later.")
    private LocalDateTime start;
    @NotNull
    @FutureOrPresent(message = "Invalid date: the date: 'end' must be today or later.")
    private LocalDateTime end;
    @NotNull
    @Positive(message = "patientId should be a positive number.")
    private int patientId;
    @NotNull
    @Positive(message = "doctorId should be a positive number.")
    private int doctorId;
}
