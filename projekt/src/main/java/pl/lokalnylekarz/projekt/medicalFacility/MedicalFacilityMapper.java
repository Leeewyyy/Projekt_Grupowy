package pl.lokalnylekarz.projekt.medicalFacility;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.lokalnylekarz.projekt.model.MedicalFacility;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MedicalFacilityMapper {

    MedicalFacilityDto fromEntityToDto(MedicalFacility medicalFacility);

    @Mapping(target = "specialists", ignore = true)
    MedicalFacilityListDto fromEntityToListDto(MedicalFacility medicalFacility);
}
