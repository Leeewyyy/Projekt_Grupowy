package pl.lokalnylekarz.projekt.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.lokalnylekarz.projekt.medicalFacility.MedicalFacilityMapper;
import pl.lokalnylekarz.projekt.model.MedicalFacility;
import pl.lokalnylekarz.projekt.model.Opinion;
import pl.lokalnylekarz.projekt.model.User;
import pl.lokalnylekarz.projekt.opinion.OpinionService;
import pl.lokalnylekarz.projekt.opinion.OpinionWithMedicalFacilityDTO;
import pl.lokalnylekarz.projekt.pojo.FavouriteMedicalFacilityPOJO;
import pl.lokalnylekarz.projekt.pojo.UserLoginPOJO;
import pl.lokalnylekarz.projekt.repository.MedicalFacilityRepository;
import pl.lokalnylekarz.projekt.repository.UserRepository;
import pl.lokalnylekarz.projekt.services.ServerInfo;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Comparator;
import java.util.List;


@RequiredArgsConstructor
@Service
public class UserService {
    public static final String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/upload/images/user-profile";

    private final UserRepository userRepository;
    private final MedicalFacilityRepository medicalFacilityRepository;
    private final OpinionService opinionService;
    private final MedicalFacilityMapper medicalFacilityMapper;

    private UserDto forDetails(User user) {
        return new UserDto(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                (user.getImage() != null) ? ServerInfo.getBaseUrl() + "/users/" + user.getId() + "/image" : null,
                user.getRegistrationDate(),
                user.getOpinions().stream().sorted(new Comparator<Opinion>() {
                    @Override
                    public int compare(Opinion o1, Opinion o2) {
                        return -o1.getAddedAt().compareTo(o2.getAddedAt());
                    }
                }).map(OpinionService::forUser).toList(),
                user.getAddedMedicalFacilities().stream().map(medicalFacilityMapper::fromEntityToListDto).toList(),
                user.getFavoriteFacilities().stream().map(medicalFacilityMapper::fromEntityToListDto).toList()
        );
    }

    public static UserDto fromEntityToDto(User user) {
        return new UserDto(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                (user.getImage() != null) ? ServerInfo.getBaseUrl() + "/users/" + user.getId() + "/image" : null,
                user.getOpinions().stream().sorted(new Comparator<Opinion>() {
                    @Override
                    public int compare(Opinion o1, Opinion o2) {
                        return -o1.getAddedAt().compareTo(o2.getAddedAt());
                    }
                }).map(OpinionService::forUser).toList(),
                user.getRegistrationDate()
        );
    }

    public static UserAddedByDto forMedicalDto(User user) {
        return new UserAddedByDto(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                (user.getImage() != null) ? ServerInfo.getBaseUrl() + "/users/" + user.getId() + "/image" : null,
                user.getRegistrationDate()
        );
    }

    public static User fromRegisterDtoToEntity(UserDtoForRegister userDtoForRegister) {
        return new User(
                userDtoForRegister.getFullName(),
                userDtoForRegister.getEmail(),
                userDtoForRegister.getPassword()
        );
    }

    public List<UserDto> getAll() {
        List<User> users = (List<User>) userRepository.findAll();

        return users.stream().map(UserService::fromEntityToDto).toList();
    }

    public UserDto get(long id) {
        User user = userRepository.findById(id).orElseThrow();

        return forDetails(user);
    }

    public User save(UserDtoForRegister userDtoForRegister) {
        if (userRepository.findByEmail(userDtoForRegister.getEmail()).isPresent()) return null;
        return userRepository.save(fromRegisterDtoToEntity(userDtoForRegister));
    }

    public UserDto loggInUser(UserLoginPOJO userLoginPOJO) {
        User user = userRepository.findByEmailAndPassword(
                userLoginPOJO.email,
                userLoginPOJO.password
        ).orElse(new User());

        if (user.getId()!=null) return fromEntityToDto(user);
        return null;
    }

    public User update(UserDtoForRegister userDtoForRegister, Long id) { //IFY DO ZMIANY JAK BĘDZIE CZAS
        if (userRepository.findById(id).isEmpty()) throw new IllegalStateException("No user with id " + id);
        User user = userRepository.findById(id).get();
        if (userDtoForRegister.getFullName() != null) user.setFullName(userDtoForRegister.getFullName());
        if (userDtoForRegister.getEmail() != null) user.setEmail(userDtoForRegister.getEmail());
        if (userDtoForRegister.getPassword() != null) user.setPassword(userDtoForRegister.getPassword());
        return userRepository.save(user);
    }

    public int addFavorite(Long userId, FavouriteMedicalFacilityPOJO facilityPOJO) {
        int added = 0;

        User user = userRepository.findById(userId).orElse(new User());

        if (user.getId() == null) {
            return added;
        }

        List<MedicalFacility> favoriteFacilities = user.getFavoriteFacilities();

        for (Long medId : facilityPOJO.getFacilitiesId()) {
            if (!medicalFacilityRepository.existsById(medId)) {
                continue;
            }

            MedicalFacility medicalFacility = (MedicalFacility) medicalFacilityRepository.findById(medId).orElse(new MedicalFacility());

            if (medicalFacility.getId() == null || favoriteFacilities.contains(medicalFacility)) {
                continue;
            }

            favoriteFacilities.add(medicalFacility);
            ++added;
        }

        user.setFavoriteFacilities(favoriteFacilities);

        userRepository.save(user);

        return added;
    }

    public int removeFavorite(Long userId, FavouriteMedicalFacilityPOJO facilityPOJO) {
        int removed = 0;

        User user = userRepository.findById(userId).orElse(new User());

        if (user.getId() == null) {
            return removed;
        }

        List<MedicalFacility> favoriteFacilities = user.getFavoriteFacilities();

        for (Long medId : facilityPOJO.getFacilitiesId()) {
            if (!medicalFacilityRepository.existsById(medId)) {
                continue;
            }

            MedicalFacility medicalFacility = (MedicalFacility) medicalFacilityRepository.findById(medId).orElse(new MedicalFacility());

            if (medicalFacility.getId() == null || !favoriteFacilities.contains(medicalFacility)) {
                continue;
            }

            favoriteFacilities.remove(medicalFacility);
            ++removed;
        }

        user.setFavoriteFacilities(favoriteFacilities);
        userRepository.save(user);

        return removed;
    }

    public List<MedicalFacility> findFavoriteFacilitiesForUser(Long userId) {
        return userRepository.findById(userId).get().getFavoriteFacilities();
    }

    public InputStream getUserImage(Long userId) {
        User user = userRepository.findById(userId).orElse(new User());

        if (user.getId() == null) {
            return null;
        }

        String userImageName = user.getImage();

        try {
            return new FileInputStream(UPLOAD_DIRECTORY + "/" + userImageName);
        } catch (IOException e) {
            return null;
        }
    }

    public boolean setUserImage(Long userId, String imageName) {
        User user = userRepository.findById(userId).orElse(new User());

        if (user.getId() == null) {
            return false;
        }

        user.setImage(imageName);
        userRepository.save(user);

        return true;
    }

    public boolean userExists(Long userId) {
        return userRepository.existsById(userId);
    }

    public List<OpinionWithMedicalFacilityDTO> getUserOpinions(Long userId) {
        User user = userRepository.findById(userId).orElse(new User());

        if (user.getId() == null) {
            return null;
        }

         return user.getOpinions().stream().sorted(new Comparator<Opinion>() {
             @Override
             public int compare(Opinion o1, Opinion o2) {
                 return -o1.getAddedAt().compareTo(o2.getAddedAt());
             }
         }).map(opinionService::fromOpinionToOpinionWithMedicalFacilityDTO).toList();
    }
}
