package pl.lokalnylekarz.projekt.medicalFacility;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.lokalnylekarz.projekt.dataTypes.Location;
import pl.lokalnylekarz.projekt.enumeration.MedicalFacilityTypes;
import pl.lokalnylekarz.projekt.enumeration.NfzStatuses;
import pl.lokalnylekarz.projekt.model.*;
import pl.lokalnylekarz.projekt.opinion.OpinionService;
import pl.lokalnylekarz.projekt.repository.MedicalFacilityRepository;
import pl.lokalnylekarz.projekt.repository.OpinionRepository;
import pl.lokalnylekarz.projekt.repository.UserRepository;
import pl.lokalnylekarz.projekt.services.Geocoder;
import pl.lokalnylekarz.projekt.services.ImageSave;
import pl.lokalnylekarz.projekt.services.SearchStatisticsService;
import pl.lokalnylekarz.projekt.specification.MedicalFacilitySpecification;
import pl.lokalnylekarz.projekt.user.UserService;

import java.io.IOException;
import java.lang.reflect.Field;
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
    private final SearchStatisticsService searchStatisticsService;
    private final ImageSave imageService;
    private final Geocoder geocoder;

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

        updateSearchStatistics(filters);

        if (filters.isSearch()) {
            List<MedicalFacility> medicalFacilitiesWithDuplicates = medicalFacilityRepository.findByNameContainingIgnoreCase(
                    filters.getSearch());
            medicalFacilitiesWithDuplicates.addAll(medicalFacilityRepository.findByAddressContainingIgnoreCase(filters.getSearch()));
            medicalFacilities = medicalFacilitiesWithDuplicates.stream().distinct().collect(Collectors.toList());
        } else
            medicalFacilities = medicalFacilityRepository.findAll(new MedicalFacilitySpecification(filters));

        List<MedicalFacilityListDto> medicalFacilityListDtos = filters.isFilterDistance()
                ? this.filterDistance(
                medicalFacilities,
                filters.getLatitude(),
                filters.getLongitude(),
                filters.getDistance()
        )
                : medicalFacilities.stream().map(medicalFacilityMapper::fromEntityToListDto).distinct().toList();

        for (MedicalFacilityListDto medicalFacilityListDto : medicalFacilityListDtos) {
            addRatingAndRatingCount(medicalFacilityListDto, medicalFacilityListDto.getId());
            medicalFacilityListDto.setSpecialists(addSpecialists(medicalFacilityListDto.getId()));
        }

        return medicalFacilityListDtos;
    }

    public List<MedicalFacilityStatisticsDTO> getAllForStatistics() {
        List<MedicalFacility> medicalFacilities = medicalFacilityRepository.findAll().stream()
                .sorted((o1, o2) -> o2.getHits() - o1.getHits())
                .toList();

        return medicalFacilities.stream().map(medicalFacilityMapper::fromEntityToStatisticsDto).toList();
    }

    public List<MedicalFacilityStatisticsDTO> getAllForStatisticsTop15() {
        List<MedicalFacility> medicalFacilities = medicalFacilityRepository.findAll()
                .stream()
                .sorted((o1, o2) -> o2.getHits() - o1.getHits())
                .limit(15)
                .toList();

        return medicalFacilities.stream().map(medicalFacilityMapper::fromEntityToStatisticsDto).toList();
    }

    public List<MedicalFacilityListDto> getAllAddedByUser(Long userId) {
        List<MedicalFacility> medicalFacilities = medicalFacilityRepository.findByUserId(userId);
        return medicalFacilities.stream().map(medicalFacilityMapper::fromEntityToListDto).toList();
    }

    public MedicalFacilityDto get(Long id) {
        MedicalFacility medicalFacility = findAndUpdateStatistics(id);

        MedicalFacilityDto medicalFacilityDto = medicalFacilityMapper.fromEntityToDto(medicalFacility);
        addRatingRatingCountAddedByOpinions(medicalFacilityDto, medicalFacility.getId());

        return medicalFacilityDto;
    }

    public MedicalFacilityDto create(MedicalFacilityForRegisterDto mDto) throws IOException {
        MedicalFacility.MedicalFacilityBuilder builder = MedicalFacility.builder();


        if (mDto.getName() != null) {
            builder.name(mDto.getName());
        }

        if (mDto.getType() != null) {
            builder.type(MedicalFacilityTypes.valueOf(mDto.getType().toUpperCase()));
        }

        if (mDto.getAddress() != null) {
            builder.address(mDto.getAddress());
        }

        if (mDto.getWebsiteUrl() != null) {
            builder.websiteUrl(mDto.getWebsiteUrl());
        }

        if (mDto.getPhone() != null) {
            builder.phone(mDto.getPhone());
        }

        if (mDto.getDescription() != null) {
            builder.description(mDto.getDescription());
        }

        if (mDto.getNfzStatus() != null) {
            builder.nfzStatus(NfzStatuses.valueOf(mDto.getNfzStatus().toUpperCase()));
        }

        if (mDto.getOpenFrom() != null) {
            builder.openFrom(mDto.getOpenFrom());
        }

        if (mDto.getOpenTo() != null) {
            builder.openTo(mDto.getOpenTo());
        }

        if (mDto.getLat() != null && mDto.getLon() != null) {
            builder.location(new Location(mDto.getLat(), mDto.getLon()));
        }

        if (mDto.getAddedBy() != null) {
            User addedBy = userRepository.findById(mDto.getAddedBy()).orElseThrow();
            builder.addedBy(addedBy);
        }

        if (mDto.getImage() != null) {
            builder.imageUrl(imageService.saveImage(mDto.getImage()));
        }

        if (mDto.getAdditionalImages() != null) {
            builder.images(imageService.saveImages(mDto.getAdditionalImages()));
        }

        MedicalFacility addedFacility = medicalFacilityRepository.save(builder.build());
        return medicalFacilityMapper.fromEntityToDto(addedFacility);
    }

    public MedicalFacilityDto edit(Long id, MedicalFacilityForRegisterDto mDto) throws IOException {
        MedicalFacility oldFacility = medicalFacilityRepository.findById(id).orElseThrow();

        MedicalFacility.MedicalFacilityBuilder builder = MedicalFacility.builder()
                .id(oldFacility.getId());

        if (mDto.getName() != null) {
            oldFacility.setName(mDto.getName());
        }

        if (mDto.getType() != null) {
            oldFacility.setType(MedicalFacilityTypes.valueOf(mDto.getType().toUpperCase()));
        }

        if (mDto.getAddress() != null) {
            oldFacility.setAddress(mDto.getAddress());
        }

        if (mDto.getWebsiteUrl() != null) {
            oldFacility.setWebsiteUrl(mDto.getWebsiteUrl());
        }

        if (mDto.getPhone() != null) {
            oldFacility.setPhone(mDto.getPhone());
        }

        if (mDto.getDescription() != null) {
            oldFacility.setDescription(mDto.getDescription());
        }

        if (mDto.getNfzStatus() != null) {
            oldFacility.setNfzStatus(NfzStatuses.valueOf(mDto.getNfzStatus().toUpperCase()));
        }

        if (mDto.getOpenFrom() != null) {
            oldFacility.setOpenFrom(mDto.getOpenFrom());
        }

        if (mDto.getOpenTo() != null) {
            oldFacility.setOpenTo(mDto.getOpenTo());
        }

        if (mDto.getLat() != null && mDto.getLon() != null) {
            oldFacility.setLocation(new Location(mDto.getLat(), mDto.getLon()));
        }

        if (mDto.getAddedBy() != null) {
            User addedBy = userRepository.findById(mDto.getAddedBy()).orElseThrow();
            oldFacility.setAddedBy(addedBy);
        }

        if (mDto.getImage() != null) {
            oldFacility.setImageUrl(imageService.saveImage(mDto.getImage()));
        }

        if (mDto.getAdditionalImages() != null) {
            oldFacility.setImages(imageService.saveImages(mDto.getAdditionalImages()));
        }

        MedicalFacility addedFacility = medicalFacilityRepository.save(oldFacility);
        return medicalFacilityMapper.fromEntityToDto(addedFacility);
    }

    public void delete(Long id) {
        MedicalFacility medicalFacility = medicalFacilityRepository.findById(id).orElseThrow();
        List<Opinion> opinions = medicalFacility.getOpinions();
        opinions.forEach(opinion -> {
            opinionRepository.deleteById(opinion.getId());
        });
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
        MedicalFacility medicalFacility = medicalFacilityRepository.findById(medicalFacilityId).orElseThrow();


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

    private MedicalFacility findAndUpdateStatistics(Long id) {
        MedicalFacility facility = medicalFacilityRepository.findById(id).orElse(new MedicalFacility());

        if (facility.getId() == null) {
            return facility;
        }

        facility.setHits(1 + facility.getHits());

        medicalFacilityRepository.save(facility);

        return facility;
    }

    private void updateSearchStatistics(MedicalFacilityFilter filters) {
        searchStatisticsService.updateGlobalSearch();

        for (Field field : filters.getClass().getDeclaredFields()) {
            SearchDetails details = new SearchDetails();
            details.setSearchType(field.getName());
            try {
                field.setAccessible(true);

                if (field.get(filters) == null || field.get(filters).toString().isEmpty()) {
                    continue;
                }

                details.setValue(field.get(filters).toString());
            } catch (IllegalAccessException ignored) {
            }

            searchStatisticsService.addOrUpdateSearchDetail(details);
        }
    }
}
