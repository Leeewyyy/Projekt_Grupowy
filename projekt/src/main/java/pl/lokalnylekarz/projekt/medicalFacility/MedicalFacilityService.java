package pl.lokalnylekarz.projekt.medicalFacility;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.lokalnylekarz.projekt.model.MedicalFacility;
import pl.lokalnylekarz.projekt.opinion.OpinionService;
import pl.lokalnylekarz.projekt.repository.MedicalFacilityRepository;
import pl.lokalnylekarz.projekt.specialist.SpecialistService;
import pl.lokalnylekarz.projekt.user.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicalFacilityService {

    private final MedicalFacilityRepository medicalFacilityRepository;

    public List<MedicalFacilityDto> getAll() {
        List<MedicalFacility> medicalFacilities = (List<MedicalFacility>) medicalFacilityRepository.findAll();

        return medicalFacilities.stream().map(MedicalFacilityService::toDtoList).toList();
    }

    public MedicalFacilityDto get(Long id) {
        MedicalFacility medicalFacility = medicalFacilityRepository.findById(id).orElse(new MedicalFacility());

        return fromEntityToDto(medicalFacility);
    }

    public static MedicalFacilityDto fromEntityToDto(MedicalFacility medicalFacility) {
        return new MedicalFacilityDto(
                medicalFacility.getId(),
                medicalFacility.getName(),
                medicalFacility.getType(),
                medicalFacility.getAddress(),
                medicalFacility.getImageUrl(),
                medicalFacility.getPhone(),
                medicalFacility.getWebsiteUrl(),
                medicalFacility.getDescription(),
                medicalFacility.getIsNFZ(),
                medicalFacility.getRating(),
                medicalFacility.getOpenFrom(),
                medicalFacility.getOpenTo(),
                medicalFacility.getLocation(),
                medicalFacility.getAddedAt(),
                medicalFacility.getSpecialist().stream().map(SpecialistService::fromEntityToDto).toList(),
                UserService.forMedicalDto(medicalFacility.getAddedBy()),
                medicalFacility.getOpinions().stream().map(OpinionService::fromEntityToDto).toList()
        );
    }

    public static MedicalFacilityDto toDtoList(MedicalFacility medicalFacility) {
        return new MedicalFacilityDto(
                medicalFacility.getId(),
                medicalFacility.getName(),
                medicalFacility.getType(),
                medicalFacility.getAddress(),
                medicalFacility.getPhone(),
                medicalFacility.getWebsiteUrl(),
                medicalFacility.getIsNFZ(),
                medicalFacility.getRating(),
                medicalFacility.getOpenFrom(),
                medicalFacility.getOpenTo(),
                medicalFacility.getLocation(),
                medicalFacility.getAddedAt()
        );
    }
}
