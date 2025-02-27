package medical.app.service;

import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import medical.app.dto.CreateVisitDto;
import medical.app.dto.DoctorPatientCounter;
import medical.app.dto.ResponseList;
import medical.app.dto.ResponseVisitDto;
import medical.app.dto.SearchParameters;
import medical.app.exception.EntityNotFoundException;
import medical.app.exception.ValidationException;
import medical.app.mapper.VisitMapper;
import medical.app.model.Doctor;
import medical.app.model.Visit;
import medical.app.repository.DoctorRepository;
import medical.app.repository.VisitRepository;
import medical.app.repository.search.specification.SpecificationBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VisitServiceImpl implements VisitService {
    private final SpecificationBuilder<Visit> specificationBuilder;
    private final VisitRepository visitRepository;
    private final DoctorRepository doctorRepository;
    private final VisitMapper visitMapper;

    @Override
    public CreateVisitDto createNewVisit(CreateVisitDto createVisitDto) {
        Doctor doctor = getDoctorFromDb(createVisitDto);
        Visit newVisit = visitMapper.toEntity(createVisitDto, ZoneId.of(doctor.getTimeZone()));
        if (!dateValidation(createVisitDto)) {
            throw new ValidationException("The start date cannot be later than the end date.");
        } else if (!isValidVisit(createVisitDto, newVisit)) {
            throw new ValidationException(String.format(
                    "The doctor already has an appointment scheduled from %s to %s.",
                    createVisitDto.getStart(), createVisitDto.getEnd()));
        } else {
            visitRepository.save(newVisit);
            return createVisitDto;
        }
    }

    @Override
    public ResponseList getInformation(SearchParameters searchDto, Pageable pageable) {
        Specification<Visit> visitSpecification = specificationBuilder.build(searchDto);
        Page<Visit> visitsBySearchParameters = visitRepository
                .findAll(visitSpecification, pageable);

        List<Integer> doctorIds = visitsBySearchParameters.stream()
                .map(v -> v.getDoctor().getId())
                .distinct()
                .toList();
        Map<Integer, Long> doctorTotalPatientMap = getDoctorTotalPatients(doctorIds);
        List<ResponseVisitDto> responseVisitDtoList = visitMapper
                .toResponseVisitDtoList(visitsBySearchParameters.toList(), doctorTotalPatientMap);
        return new ResponseList(responseVisitDtoList, visitsBySearchParameters.getTotalElements());
    }

    private Map<Integer, Long> getDoctorTotalPatients(List<Integer> doctorIds) {
        return doctorRepository.findDoctorsWithPatientCount(doctorIds)
                .stream()
                .collect(Collectors.toMap(
                        DoctorPatientCounter::getDoctorId,
                        DoctorPatientCounter::getTotalPatients));
    }

    private Doctor getDoctorFromDb(CreateVisitDto createVisitDto) {
        Doctor doctor = doctorRepository.findById(createVisitDto.getDoctorId()).orElseThrow(
                () -> new EntityNotFoundException(String.format(
                        "Doctor with ID %d is not found in the database.",
                        createVisitDto.getDoctorId())));
        return doctor;
    }

    private boolean isValidVisit(CreateVisitDto createVisitDto, Visit newVisit) {
        boolean isValid = visitRepository.findNextVisitForDoctor(createVisitDto.getDoctorId(),
                newVisit.getStartDateTime(), newVisit.getEndDateTime()).isEmpty();
        return isValid;
    }

    private boolean dateValidation(CreateVisitDto createVisitDto) {
        return createVisitDto.getStart().isBefore(createVisitDto.getEnd());
    }
}
