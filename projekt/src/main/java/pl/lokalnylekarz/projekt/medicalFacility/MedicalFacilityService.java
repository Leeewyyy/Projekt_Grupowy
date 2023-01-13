package pl.lokalnylekarz.projekt.medicalFacility;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.lokalnylekarz.projekt.dataTypes.Location;
import pl.lokalnylekarz.projekt.enumeration.MedicalFacilityTypes;
import pl.lokalnylekarz.projekt.enumeration.Specialization;
import pl.lokalnylekarz.projekt.model.MedicalFacility;
import pl.lokalnylekarz.projekt.opinion.OpinionService;
import pl.lokalnylekarz.projekt.repository.MedicalFacilityRepository;
import pl.lokalnylekarz.projekt.specialist.SpecialistService;
import pl.lokalnylekarz.projekt.user.UserService;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MedicalFacilityService {

    private final MedicalFacilityRepository medicalFacilityRepository;

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
                medicalFacility.getOpinions().stream().map(OpinionService::fromEntityToDto).toList(),
                medicalFacility.getFavoriteFor().stream().map(UserService::fromEntityToDto).toList()
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
                medicalFacility.getAddedAt(),
                medicalFacility.getFavoriteFor().stream().map(UserService::fromEntityToDto).toList()
        );
    }

    public List<MedicalFacilityDto> getAll() {
        List<MedicalFacility> medicalFacilities = (List<MedicalFacility>) medicalFacilityRepository.findAll();

        return medicalFacilities.stream().map(MedicalFacilityService::toDtoList).toList();
    }

    public List<MedicalFacilityDto> getAll(Map<String, String> filters) {
        String typeFilter = filters.get("type");
        String specializationFilter = filters.get("specialization");
        String lonFilter = filters.get("lon");
        String latFilter = filters.get("lat");
        String distanceFilter = filters.get("distance");

        try {
            if (typeFilter == null && specializationFilter == null) {
                List<MedicalFacility> medicalFacilities = medicalFacilityRepository.findByTypeAndSpecialistSpecialization(
                        MedicalFacilityTypes.valueOf(typeFilter),
                        Specialization.valueOf(specializationFilter)
                );

                if (latFilter == null && lonFilter == null && distanceFilter == null) {
                    return medicalFacilities.stream().map(MedicalFacilityService::toDtoList).toList();
                }

                return this.filterDistance(
                        medicalFacilities,
                        Double.parseDouble(lonFilter),
                        Double.parseDouble(latFilter),
                        Double.parseDouble(distanceFilter)
                );
            }

            if (typeFilter != null) {
                List<MedicalFacility> medicalFacilities = medicalFacilityRepository.findByType(
                        MedicalFacilityTypes.valueOf(typeFilter)
                );


                if (latFilter == null && lonFilter == null && distanceFilter == null) {
                    return medicalFacilities.stream().map(MedicalFacilityService::toDtoList).toList();
                }

                return this.filterDistance(
                        medicalFacilities,
                        Double.parseDouble(lonFilter),
                        Double.parseDouble(latFilter),
                        Double.parseDouble(distanceFilter)
                );
            }

            if (specializationFilter != null) {
                List<MedicalFacility> medicalFacilities = medicalFacilityRepository.findBySpecialistSpecialization(
                        Specialization.valueOf(specializationFilter)
                );


                if (latFilter == null && lonFilter == null && distanceFilter == null) {
                    return medicalFacilities.stream().map(MedicalFacilityService::toDtoList).toList();
                }

                return this.filterDistance(
                        medicalFacilities,
                        Double.parseDouble(lonFilter),
                        Double.parseDouble(latFilter),
                        Double.parseDouble(distanceFilter)
                );
            }
        } catch (IllegalArgumentException $e) {
            return null;
        }

        return null;
    }

    public MedicalFacilityDto get(Long id) {
        MedicalFacility medicalFacility = medicalFacilityRepository.findById(id).orElse(new MedicalFacility());

        return fromEntityToDto(medicalFacility);
    }

    protected List<MedicalFacilityDto> filterDistance(
            List<MedicalFacility> medicalFacilities,
            double lon,
            double lat,
            double distance
    ) {
        Location location = new Location(lat, lon);

        return medicalFacilities.stream().filter(medicalFacility -> {
            return medicalFacility.getLocation().distanceTo(location) <= distance;
        }).map(MedicalFacilityService::toDtoList).toList();
    }
}
