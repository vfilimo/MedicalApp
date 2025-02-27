package medical.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import medical.app.dto.CreateVisitDto;
import medical.app.dto.ResponseList;
import medical.app.dto.SearchParameters;
import medical.app.service.VisitService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Medical Application", description = "Endpoints for visits management")
@RestController
@RequestMapping("/visits")
@RequiredArgsConstructor
public class Controller {
    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_PAGE_SIZE = 20;
    private static final String DEFAULT_SORT = "id";
    private final VisitService visitService;

    @Operation(summary = "Create new visit", description = "Create new visit for doctor. "
            + "Validated by date(can't be in the past) "
            + "and doctor and patient Ids (can't be negative)")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateVisitDto createNewVisit(@RequestBody @Valid CreateVisitDto createVisit) {
        return visitService.createNewVisit(createVisit);
    }

    @Operation(summary = "Find last visit by search parameters", description =
            "Search for patients by first name, last name, and doctors. "
                    + "The search is validated to ensure that the provided names "
                    + "and doctor IDs are valid (doctor IDs can't be negative).")
    @GetMapping("/search")
    public ResponseList getInformationAboutPatient(
            @PageableDefault(size = DEFAULT_PAGE_SIZE, page = DEFAULT_PAGE, sort = DEFAULT_SORT)
            Pageable pageable, SearchParameters searchDto) {
        return visitService.getInformation(searchDto, pageable);
    }
}
