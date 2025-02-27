package medical.app.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseList {
    private List<ResponseVisitDto> data;
    private Long count;
}
