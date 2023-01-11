package pl.lokalnylekarz.projekt.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.lokalnylekarz.projekt.medicalFacility.MedicalFacilityService;
import pl.lokalnylekarz.projekt.model.MedicalFacility;
import pl.lokalnylekarz.projekt.model.User;
import pl.lokalnylekarz.projekt.opinion.OpinionService;
import pl.lokalnylekarz.projekt.repository.MedicalFacilityRepository;
import pl.lokalnylekarz.projekt.repository.UserRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final MedicalFacilityRepository medicalFacilityRepository;

    public List<UserDto> getAll() {
        List<User> users = (List<User>) userRepository.findAll();

        return users.stream().map(UserService::fromEntityToDto).toList();
    }

    public UserDto get(long id) {
        User user = userRepository.findById(id).orElseThrow();

        return forDetails(user);
    }

    public User save(UserDtoForRegister userDtoForRegister) {
        return userRepository.save(fromRegisterDtoToEntity(userDtoForRegister));
    }

    public Boolean checkUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user == null) return false;
        return user.getPassword().equals(password);
    }

    public User update(UserDtoForRegister userDtoForRegister, Long id) { //IFY DO ZMIANY JAK BĘDZIE CZAS
        if (userRepository.findById(id).isEmpty()) throw new IllegalStateException("No user with id " + id);
        User user = userRepository.findById(id).get();
        if (userDtoForRegister.getLogin() != null) user.setLogin(userDtoForRegister.getLogin());
        if (userDtoForRegister.getFirstName() != null) user.setFirstname(userDtoForRegister.getFirstName());
        if (userDtoForRegister.getLastName() != null) user.setLastname(userDtoForRegister.getLastName());
        if (userDtoForRegister.getEmail() != null) user.setEmail(userDtoForRegister.getEmail());
        if (userDtoForRegister.getPassword() != null) user.setPassword(userDtoForRegister.getPassword());
        return userRepository.save(user);
    }

    public User changeFavorite(Long userId, Long facilityId) { //NIE DZIAŁA JAK POWINNO
        if (userRepository.findById(userId).isEmpty()) throw new IllegalStateException("No user with id " + userId);
        if (medicalFacilityRepository.findById(facilityId).isEmpty()) throw new IllegalStateException("No facility with id " + facilityId);
        List<MedicalFacility> favoriteFacilites = userRepository.findById(userId).get().getFavoriteFacilities();
        MedicalFacility facility = medicalFacilityRepository.findById(facilityId).get();
        if (favoriteFacilites.contains(facility)) favoriteFacilites.remove(facility);
        else favoriteFacilites.add(facility);
        User user = userRepository.findById(userId).get();
        user.setFavoriteFacilities(favoriteFacilites);
        return userRepository.save(user);
    }

    public List<MedicalFacility> findFavoriteFacilitiesForUser(Long userId) {
        return userRepository.findById(userId).get().getFavoriteFacilities();
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

    public static User fromRegisterDtoToEntity(UserDtoForRegister userDtoForRegister){
        return new User(
                userDtoForRegister.getLogin(),
                userDtoForRegister.getFirstName(),
                userDtoForRegister.getLastName(),
                userDtoForRegister.getEmail(),
                userDtoForRegister.getPassword()
        );
    }
}
