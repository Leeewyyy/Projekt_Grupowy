package pl.lokalnylekarz.projekt.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.lokalnylekarz.projekt.medicalFacility.MedicalFacilityService;
import pl.lokalnylekarz.projekt.model.User;
import pl.lokalnylekarz.projekt.opinion.OpinionService;
import pl.lokalnylekarz.projekt.repository.UserRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public List<UserDto> getAll() {
        List<User> users = (List<User>) userRepository.findAll();

        return users.stream().map(UserService::fromEntityToDto).toList();
    }

    public UserDto get(long id) {
        User user = userRepository.findById(id).orElse(new User());

        return forDetails(user);
    }

    private static UserDto forDetails(User user) {
        return new UserDto(
                user.getId(),
                user.getLogin(),
                user.getFirstname(),
                user.getLastname(),
                user.getEmail(),
                user.getRegistrationDate(),
                user.getOpinions().stream().map(OpinionService::forUser).toList(),
                user.getAddedMedicalFacilities().stream().map(MedicalFacilityService::toDtoList).toList()
        );
    }

    public static UserDto fromEntityToDto(User user) {
        return new UserDto(
                user.getId(),
                user.getLogin(),
                user.getFirstname(),
                user.getLastname(),
                user.getEmail(),
                user.getRegistrationDate()
        );
    }

    public static UserDto forMedicalDto(User user) {
        return new UserDto(
                user.getId(),
                user.getLogin(),
                user.getFirstname(),
                user.getLastname(),
                user.getEmail(),
                user.getRegistrationDate()
        );
    }
}
