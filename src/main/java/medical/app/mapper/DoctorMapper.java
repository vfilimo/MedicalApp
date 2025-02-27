package medical.app.mapper;

import java.util.Map;
import medical.app.config.MapperConfig;
import medical.app.dto.DoctorDto;
import medical.app.model.Doctor;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class)
public interface DoctorMapper {
    @Named("toDoctorDto")
    @Mapping(target = "totalPatients", source = "doctor.id", qualifiedByName = "setTotalPatient")
    DoctorDto toDoctorDto(Doctor doctor, @Context Map<Integer, Long> doctorsPatientsMap);

    @Named("doctorById")
    default Doctor createDoctorById(int id) {
        return new Doctor(id);
    }

    @Named("setTotalPatient")
    default Long setTotalPatients(int doctorId,
                                  @Context Map<Integer, Long> doctorsPatientsMap) {
        return doctorsPatientsMap.get(doctorId);
    }
}
