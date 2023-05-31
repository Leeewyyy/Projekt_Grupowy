package pl.lokalnylekarz.projekt.medicalFacility;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.lokalnylekarz.projekt.dataTypes.Location;
import pl.lokalnylekarz.projekt.enumeration.MedicalFacilityTypes;
import pl.lokalnylekarz.projekt.enumeration.Specialization;
import pl.lokalnylekarz.projekt.model.MedicalFacility;
import pl.lokalnylekarz.projekt.model.Opinion;
import pl.lokalnylekarz.projekt.opinion.OpinionService;
import pl.lokalnylekarz.projekt.repository.MedicalFacilityRepository;
import pl.lokalnylekarz.projekt.specialist.SpecialistService;
import pl.lokalnylekarz.projekt.specification.MedicalFacilitySpecification;
import pl.lokalnylekarz.projekt.user.UserService;

import java.util.Comparator;
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
                medicalFacility.getImages(),
                medicalFacility.getPhone(),
                medicalFacility.getWebsiteUrl(),
                medicalFacility.getDescription(),
                medicalFacility.getNfzStatus(),
                medicalFacility.getRating(),
                medicalFacility.getOpenFrom(),
                medicalFacility.getOpenTo(),
                medicalFacility.getLocation(),
                medicalFacility.getAddedAt(),
                medicalFacility.getSpecialist().stream().map(SpecialistService::fromEntityToDto).toList(),
                UserService.forMedicalDto(medicalFacility.getAddedBy()),
                medicalFacility.getOpinions().stream().sorted(new Comparator<Opinion>() {
                    @Override
                    public int compare(Opinion o1, Opinion o2) {
                        return -o1.getAddedAt().compareTo(o2.getAddedAt());
                    }
                }).map(OpinionService::fromEntityToDto).toList()
        );
    }

    public static MedicalFacilityDto toDtoList(MedicalFacility medicalFacility) {
        return new MedicalFacilityDto(
                medicalFacility.getId(),
                medicalFacility.getName(),
                medicalFacility.getType(),
                medicalFacility.getAddress(),
                medicalFacility.getImageUrl(),
                medicalFacility.getPhone(),
                medicalFacility.getWebsiteUrl(),
                medicalFacility.getNfzStatus(),
                medicalFacility.getRating(),
                medicalFacility.getOpenFrom(),
                medicalFacility.getOpenTo(),
                medicalFacility.getLocation(),
                medicalFacility.getAddedAt()
        );
    }

    public List<MedicalFacilityDto> getAll() {
        List<MedicalFacility> medicalFacilities = (List<MedicalFacility>) medicalFacilityRepository.findAll();

        return medicalFacilities.stream().map(MedicalFacilityService::toDtoList).toList();
    }

    public List<MedicalFacilityDto> getAll(MedicalFacilityFilter filters) {
        List<MedicalFacility> medicalFacilities = medicalFacilityRepository.findAll(new MedicalFacilitySpecification(filters));

        if (filters.isFilterDistance()) {
            return this.filterDistance(
                    medicalFacilities,
                    filters.getLatitude(),
                    filters.getLongitude(),
                    filters.getDistance()
            );
        }

        return medicalFacilities.stream().map(MedicalFacilityService::toDtoList).toList();
    }

    public MedicalFacilityDto get(Long id) {
        MedicalFacility medicalFacility = medicalFacilityRepository.findById(id).orElse(new MedicalFacility());

        return fromEntityToDto(medicalFacility);
    }

    protected List<MedicalFacilityDto> filterDistance(
            List<MedicalFacility> medicalFacilities,
            double lat,
            double lon,
            double distance
    ) {
        Location location = new Location(lat, lon);

        return medicalFacilities.stream().filter(medicalFacility -> {
            return medicalFacility.getLocation().distanceTo(location) <= distance;
        }).map(MedicalFacilityService::toDtoList).toList();
    }
}
