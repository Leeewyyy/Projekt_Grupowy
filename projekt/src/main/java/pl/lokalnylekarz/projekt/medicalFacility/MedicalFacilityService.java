package pl.lokalnylekarz.projekt.medicalFacility;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lokalnylekarz.projekt.dataTypes.Location;
import pl.lokalnylekarz.projekt.enumeration.MedicalFacilityTypes;
import pl.lokalnylekarz.projekt.enumeration.Specialization;
import pl.lokalnylekarz.projekt.model.MedicalFacility;
import pl.lokalnylekarz.projekt.model.Opinion;
import pl.lokalnylekarz.projekt.opinion.OpinionService;
import pl.lokalnylekarz.projekt.repository.MedicalFacilityRepository;
import pl.lokalnylekarz.projekt.repository.OpinionRepository;
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
    private final OpinionRepository opinionRepository;
    private final MedicalFacilityMapper medicalFacilityMapper;

    public List<MedicalFacilityListDto> getAll() {
        List<MedicalFacility> medicalFacilities = (List<MedicalFacility>) medicalFacilityRepository.findAll();

        List<MedicalFacilityListDto> medicalFacilityListDtos = medicalFacilities.stream().map(medicalFacilityMapper::fromEntityToListDto).toList();

        for (MedicalFacilityListDto medicalFacilityListDto : medicalFacilityListDtos) {
            addRatingAndRatingCount(medicalFacilityListDto, medicalFacilityListDto.getId());
        }

        return medicalFacilityListDtos;
    }

    public List<MedicalFacilityListDto> getAll(MedicalFacilityFilter filters) {
        List<MedicalFacility> medicalFacilities = medicalFacilityRepository.findAll(new MedicalFacilitySpecification(filters));

        if (filters.isFilterDistance()) {
            return this.filterDistance(
                    medicalFacilities,
                    filters.getLatitude(),
                    filters.getLongitude(),
                    filters.getDistance()
            );
        }

        List<MedicalFacilityListDto> medicalFacilityListDtos = medicalFacilities.stream().map(medicalFacilityMapper::fromEntityToListDto).toList();

        for (MedicalFacilityListDto medicalFacilityListDto : medicalFacilityListDtos) {
            addRatingAndRatingCount(medicalFacilityListDto, medicalFacilityListDto.getId());
        }

        return medicalFacilityListDtos;
    }

    public MedicalFacilityDto get(Long id) {
        MedicalFacility medicalFacility = medicalFacilityRepository.findById(id).orElse(new MedicalFacility());

        MedicalFacilityDto medicalFacilityDto = medicalFacilityMapper.fromEntityToDto(medicalFacility);
        addRatingRatingCountAddedByOpinions(medicalFacilityDto, medicalFacility.getId());

        return medicalFacilityDto;
    }

    protected List<MedicalFacilityListDto> filterDistance(
            List<MedicalFacility> medicalFacilities,
            double lat,
            double lon,
            double distance
    ) {
        Location location = new Location(lat, lon);

        return medicalFacilities.stream().filter(medicalFacility -> {
            return medicalFacility.getLocation().distanceTo(location) <= distance;
        }).map(medicalFacilityMapper::fromEntityToListDto).toList();
    }

    public void addRatingRatingCountAddedByOpinions(MedicalFacilityDto medicalFacilityDto, Long medicalFacilityId) {
        MedicalFacility medicalFacility = medicalFacilityRepository.findById(medicalFacilityId).orElse(null);

        Long ratingsSum = opinionRepository.sumRatingsByMedicalFacility(medicalFacilityId);
        Long ratingsCount = opinionRepository.countByMedicalFacility(medicalFacilityId);
        Float rating = ratingsSum.floatValue() / ratingsCount.floatValue();

        medicalFacilityDto.setRating(rating);
        medicalFacilityDto.setRatingCount(ratingsCount);
        medicalFacilityDto.setAddedBy(UserService.forMedicalDto(medicalFacility.getAddedBy()));
        medicalFacilityDto.setOpinions(
                medicalFacility.getOpinions().stream().sorted(new Comparator<Opinion>() {
                    @Override
                    public int compare(Opinion o1, Opinion o2) {
                        return -o1.getAddedAt().compareTo(o2.getAddedAt());
                    }
                }).map(OpinionService::fromEntityToDto).toList()
        );
    }

    public void addRatingAndRatingCount(MedicalFacilityListDto medicalFacilityListDto, Long medicalFacilityId) {
        MedicalFacility medicalFacility = medicalFacilityRepository.findById(medicalFacilityId).orElse(null);

        Long ratingsSum = opinionRepository.sumRatingsByMedicalFacility(medicalFacilityId);
        Long ratingsCount = opinionRepository.countByMedicalFacility(medicalFacilityId);
        Float rating = ratingsSum.floatValue() / ratingsCount.floatValue();

        medicalFacilityListDto.setRating(rating);
        medicalFacilityListDto.setRatingCount(ratingsCount);
    }
}
