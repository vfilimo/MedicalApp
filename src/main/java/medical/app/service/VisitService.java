package medical.app.service;

import medical.app.dto.CreateVisitDto;
import medical.app.dto.ResponseList;
import medical.app.dto.SearchParameters;
import org.springframework.data.domain.Pageable;

public interface VisitService {
    CreateVisitDto createNewVisit(CreateVisitDto createVisitDto);

    ResponseList getInformation(SearchParameters searchDto, Pageable pageable);
}
