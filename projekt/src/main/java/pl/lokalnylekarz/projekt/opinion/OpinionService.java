package pl.lokalnylekarz.projekt.opinion;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.lokalnylekarz.projekt.model.MedicalFacility;
import pl.lokalnylekarz.projekt.model.Opinion;
import pl.lokalnylekarz.projekt.model.User;
import pl.lokalnylekarz.projekt.repository.MedicalFacilityRepository;
import pl.lokalnylekarz.projekt.repository.OpinionRepository;
import pl.lokalnylekarz.projekt.repository.UserRepository;
import pl.lokalnylekarz.projekt.user.UserService;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OpinionService {

    private final OpinionRepository opinionRepository;
    private final UserRepository userRepository;
    private final MedicalFacilityRepository medicalFacilityRepository;

    public Opinion save(Long userId, Long facilityId, OpinionFromFrontDto opinionFromFrontDto) {
        User addedBy = userRepository.findById(userId).get();
        MedicalFacility medicalFacility = medicalFacilityRepository.findById(facilityId).get();
        return opinionRepository.save(fromOpinionFromFrontDtoToEntity(opinionFromFrontDto, addedBy, medicalFacility));
    }

    public static OpinionDto fromEntityToDto(Opinion opinion) {
        return new OpinionDto(
                opinion.getId(),
                UserService.fromEntityToDto(opinion.getAddedBy()),
                opinion.getRating(),
                opinion.getDescription(),
                opinion.getAddedAt()
        );
    }

    public static OpinionDto forUser(Opinion opinion) {
        return new OpinionDto(
                opinion.getId(),
                opinion.getRating(),
                opinion.getDescription(),
                opinion.getAddedAt()
        );
    }

    public static Opinion fromOpinionFromFrontDtoToEntity(OpinionFromFrontDto opinionFromFrontDto, User user, MedicalFacility medicalFacility) {
        return Opinion.builder()
                .addedBy(user)
                .rating(opinionFromFrontDto.getRating())
                .description(opinionFromFrontDto.getText())
                .medicalFacility(medicalFacility)
                .build();
    }
}
