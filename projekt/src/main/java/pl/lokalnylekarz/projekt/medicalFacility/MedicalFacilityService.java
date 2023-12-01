package pl.lokalnylekarz.projekt.medicalFacility;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.lokalnylekarz.projekt.dataTypes.Location;
import pl.lokalnylekarz.projekt.enumeration.MedicalFacilityTypes;
import pl.lokalnylekarz.projekt.enumeration.NfzStatuses;
import pl.lokalnylekarz.projekt.model.MedicalFacility;
import pl.lokalnylekarz.projekt.model.Opinion;
import pl.lokalnylekarz.projekt.model.Specialist;
import pl.lokalnylekarz.projekt.model.User;
import pl.lokalnylekarz.projekt.opinion.OpinionService;
import pl.lokalnylekarz.projekt.repository.MedicalFacilityRepository;
import pl.lokalnylekarz.projekt.repository.OpinionRepository;
import pl.lokalnylekarz.projekt.repository.UserRepository;
import pl.lokalnylekarz.projekt.specification.MedicalFacilitySpecification;
import pl.lokalnylekarz.projekt.user.UserService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicalFacilityService {

    private final MedicalFacilityRepository medicalFacilityRepository;
    private final UserRepository userRepository;
    private final OpinionRepository opinionRepository;
    private final MedicalFacilityMapper medicalFacilityMapper;
    private final OpinionService opinionService;

    public List<MedicalFacilityListDto> getAll() {
        List<MedicalFacility> medicalFacilities = (List<MedicalFacility>) medicalFacilityRepository.findAll();

        List<MedicalFacilityListDto> medicalFacilityListDtos = medicalFacilities.stream().map(medicalFacilityMapper::fromEntityToListDto).toList();

        for (MedicalFacilityListDto medicalFacilityListDto : medicalFacilityListDtos) {
            addRatingAndRatingCount(medicalFacilityListDto, medicalFacilityListDto.getId());
            medicalFacilityListDto.setSpecialists(addSpecialists(medicalFacilityListDto.getId()));
        }

        return medicalFacilityListDtos;
    }

    public List<MedicalFacilityListDto> getAll(MedicalFacilityFilter filters) {

        List<MedicalFacility> medicalFacilities;

        if (filters.isSearch()) {
            List<MedicalFacility> medicalFacilitiesWithDuplicates = medicalFacilityRepository.findByNameContainingIgnoreCase(filters.getSearch());
            medicalFacilitiesWithDuplicates.addAll(medicalFacilityRepository.findByAddressContainingIgnoreCase(filters.getSearch()));
            medicalFacilities = medicalFacilitiesWithDuplicates.stream().distinct().collect(Collectors.toList());
        }
        else
            medicalFacilities = medicalFacilityRepository.findAll(new MedicalFacilitySpecification(filters));

        List<MedicalFacilityListDto> medicalFacilityListDtos = filters.isFilterDistance()
                ? this.filterDistance(medicalFacilities, filters.getLatitude(), filters.getLongitude(), filters.getDistance())
                : medicalFacilities.stream().map(medicalFacilityMapper::fromEntityToListDto).distinct().toList();

        for (MedicalFacilityListDto medicalFacilityListDto : medicalFacilityListDtos) {
            addRatingAndRatingCount(medicalFacilityListDto, medicalFacilityListDto.getId());
            medicalFacilityListDto.setSpecialists(addSpecialists(medicalFacilityListDto.getId()));
        }

        return medicalFacilityListDtos;
    }

    public List<MedicalFacilityListDto> getAllAddedByUser(Long userId) {
        List<MedicalFacility> medicalFacilities = medicalFacilityRepository.findByUserId(userId);
        return medicalFacilities.stream().map(medicalFacilityMapper::fromEntityToListDto).toList();
    }

    public MedicalFacilityDto get(Long id) {
        MedicalFacility medicalFacility = medicalFacilityRepository.findById(id).orElse(new MedicalFacility());

        MedicalFacilityDto medicalFacilityDto = medicalFacilityMapper.fromEntityToDto(medicalFacility);
        addRatingRatingCountAddedByOpinions(medicalFacilityDto, medicalFacility.getId());

        return medicalFacilityDto;
    }

    public MedicalFacilityDto create(MedicalFacilityForRegisterDto mDto) {
        User addedBy = userRepository.findById(mDto.getAddedBy()).orElseThrow();

        MedicalFacility newFacility = MedicalFacility.builder()
                .name(mDto.getName())
                .type(MedicalFacilityTypes.valueOf(mDto.getType().toUpperCase()))
                .address(mDto.getAddress())
                .phone(mDto.getPhone())
                .websiteUrl(mDto.getWebsiteUrl())
                .description(mDto.getDescription())
                .nfzStatus(NfzStatuses.valueOf(mDto.getNfzStatus().toUpperCase()))
                .openFrom(mDto.getOpenFrom())
                .openTo(mDto.getOpenTo())
                .location(new Location(mDto.getLat(), mDto.getLon()))
                .addedBy(addedBy)
                .imageUrl(mDto.getImageUrl())
                .build();

        MedicalFacility addedFacility = medicalFacilityRepository.save(newFacility);
        return medicalFacilityMapper.fromEntityToDto(addedFacility);
    }

    public MedicalFacilityDto edit(Long id, MedicalFacilityForRegisterDto mDto) {
        MedicalFacility oldFacility = medicalFacilityRepository.findById(id).orElseThrow();
        User addedBy = userRepository.findById(mDto.getAddedBy()).orElseThrow();

        MedicalFacility newFacility = MedicalFacility.builder()
                .id(oldFacility.getId())
                .name(mDto.getName())
                .type(MedicalFacilityTypes.valueOf(mDto.getType().toUpperCase()))
                .address(mDto.getAddress())
                .phone(mDto.getPhone())
                .websiteUrl(mDto.getWebsiteUrl())
                .description(mDto.getDescription())
                .nfzStatus(NfzStatuses.valueOf(mDto.getNfzStatus().toUpperCase()))
                .openFrom(mDto.getOpenFrom())
                .openTo(mDto.getOpenTo())
                .location(new Location(mDto.getLat(), mDto.getLon()))
                .addedBy(addedBy)
                .imageUrl(mDto.getImageUrl())
                .images(oldFacility.getImages())
                .build();

        MedicalFacility addedFacility = medicalFacilityRepository.save(newFacility);
        return medicalFacilityMapper.fromEntityToDto(addedFacility);
    }

    public void delete(Long id) {
        MedicalFacility medicalFacility = medicalFacilityRepository.findById(id).orElseThrow();
        medicalFacilityRepository.deleteById(id);
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
        }).map(medicalFacilityMapper::fromEntityToListDto).distinct().toList();
    }

    public void addRatingRatingCountAddedByOpinions(MedicalFacilityDto medicalFacilityDto, Long medicalFacilityId) {
        MedicalFacility medicalFacility = medicalFacilityRepository.findById(medicalFacilityId).orElse(null);


        Long ratingsSum = opinionRepository.sumRatingsByMedicalFacility(medicalFacilityId);
        Long ratingsCount = opinionRepository.countByMedicalFacility(medicalFacilityId);
        if (ratingsSum != null && ratingsCount != null) {
            float rating = ratingsSum.floatValue() / ratingsCount.floatValue();
            medicalFacilityDto.setRating(rating);
            medicalFacilityDto.setRatingCount(ratingsCount);
        }

        medicalFacilityDto.setAddedBy(UserService.forMedicalDto(medicalFacility.getAddedBy()));
        medicalFacilityDto.setOpinions(
                medicalFacility.getOpinions().stream().sorted(new Comparator<Opinion>() {
                    @Override
                    public int compare(Opinion o1, Opinion o2) {
                        return -o1.getAddedAt().compareTo(o2.getAddedAt());
                    }
                }).map(opinionService::fromEntityToDto).toList()
        );
    }

    public void addRatingAndRatingCount(MedicalFacilityListDto medicalFacilityListDto, Long medicalFacilityId) {

        Long ratingsSum = opinionRepository.sumRatingsByMedicalFacility(medicalFacilityId);
        Long ratingsCount = opinionRepository.countByMedicalFacility(medicalFacilityId);

        if (ratingsSum == null || ratingsCount == null) return;

        Float rating = ratingsSum.floatValue() / ratingsCount.floatValue();

        medicalFacilityListDto.setRating(rating);
        medicalFacilityListDto.setRatingCount(ratingsCount);
    }

    public List<String> addSpecialists(Long medicalFacilityId) {

        List<String> specialists = new ArrayList<>();

        MedicalFacility medicalFacility = medicalFacilityRepository.findById(medicalFacilityId).orElse(null);

        List<Specialist> specialistList = medicalFacility.getSpecialists();

        for (Specialist specialist : specialistList) {
            String specialization = specialist.getSpecialization().toString();
            if (!specialists.contains(specialization)) specialists.add(specialization);
        }

        return specialists;
    }
}
