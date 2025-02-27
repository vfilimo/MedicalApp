package medical.app.mapper;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import medical.app.config.MapperConfig;
import medical.app.dto.CreateVisitDto;
import medical.app.dto.ResponseVisitDto;
import medical.app.dto.VisitDto;
import medical.app.model.Visit;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class, uses = {DoctorMapper.class, PatientMapper.class})
public interface VisitMapper {
    @Mappings({@Mapping(target = "patient", source = "patientId", qualifiedByName = "patientById"),
            @Mapping(target = "doctor", source = "doctorId", qualifiedByName = "doctorById"),
            @Mapping(target = "startDateTime", source = "start", qualifiedByName = "toDoctorTime"),
            @Mapping(target = "endDateTime", source = "end", qualifiedByName = "toDoctorTime"),
            @Mapping(target = "id", ignore = true)})
    Visit toEntity(CreateVisitDto createVisitDto, @Context ZoneId zoneId);

    @Mappings({@Mapping(target = "firstName", source = "patient.firstName"),
            @Mapping(target = "lastName", source = "patient.lastName"),
            @Mapping(target = "lastVisit", source = "visit", qualifiedByName = "toVisitDto")
    })
    ResponseVisitDto toResponseVisitDto(Visit visit,
                                        @Context Map<Integer, Long> doctorsPatientsMap);

    List<ResponseVisitDto> toResponseVisitDtoList(List<Visit> visitsList,
                                                  @Context Map<Integer, Long> doctorsPatientsMap);

    @Mappings({@Mapping(target = "start", source = "startDateTime"),
            @Mapping(target = "end", source = "endDateTime"),
            @Mapping(target = "doctor", source = "doctor", qualifiedByName = "toDoctorDto")
    })
    @Named("toVisitDto")
    VisitDto toVisitDto(Visit visit, @Context Map<Integer, Long> doctorsPatientsMap);

    @Named("toDoctorTime")
    default LocalDateTime convertToTimeZone(LocalDateTime localDateTime, @Context ZoneId zoneId) {
        ZoneId userZone = ZoneId.systemDefault();
        ZonedDateTime userZonedDateTime = localDateTime.atZone(userZone);
        ZonedDateTime doctorZonedDateTime = userZonedDateTime.withZoneSameInstant(zoneId);
        return doctorZonedDateTime.toLocalDateTime();
    }
}
