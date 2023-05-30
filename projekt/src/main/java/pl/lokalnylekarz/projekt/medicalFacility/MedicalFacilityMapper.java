package pl.lokalnylekarz.projekt.medicalFacility;

import org.mapstruct.Mapper;
import pl.lokalnylekarz.projekt.model.MedicalFacility;

@Mapper
public interface MedicalFacilityMapper {

    MedicalFacilityDto fromEntityToDto(MedicalFacility medicalFacility);

    MedicalFacilityListDto fromEntityToListDto(MedicalFacility medicalFacility);
}
