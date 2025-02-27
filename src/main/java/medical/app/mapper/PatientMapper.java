package medical.app.mapper;

import medical.app.config.MapperConfig;
import medical.app.model.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class)
public interface PatientMapper {
    @Named("patientById")
    default Patient createPatientById(int id) {
        return new Patient(id);
    }
}
