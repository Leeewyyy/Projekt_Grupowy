package pl.lokalnylekarz.projekt.user;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.lokalnylekarz.projekt.api.Endpoint;
import pl.lokalnylekarz.projekt.medicalFacility.MedicalFacilityListDto;
import pl.lokalnylekarz.projekt.model.MedicalFacility;
import pl.lokalnylekarz.projekt.model.User;
import pl.lokalnylekarz.projekt.opinion.OpinionWithMedicalFacilityDTO;
import pl.lokalnylekarz.projekt.pojo.FavouriteMedicalFacilityPOJO;
import pl.lokalnylekarz.projekt.pojo.UserLoginPOJO;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(Endpoint.USERS_BASE_ROUTE)
public class UserController {

    private final UserService userService;

    @GetMapping()
    @ResponseBody
    public List<UserDto> getUsers() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<UserDto> getUserDetails(@PathVariable(value = "id") Long id) {
        try {
            return ResponseEntity.ok(userService.get(id));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<User> saveUser(@RequestBody UserDtoForRegister userDtoForRegister) throws IllegalAccessException {

        if (userDtoForRegister.getFullName() == null || userDtoForRegister.getFullName().equals("")) return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        if (userDtoForRegister.getEmail() == null || userDtoForRegister.getEmail().equals("")) return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        if (userDtoForRegister.getPassword() == null || userDtoForRegister.getPassword().equals("")) return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

        User dto = userService.save(userDtoForRegister);
        if (dto==null) return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody UserLoginPOJO userLoginPOJO) {
        UserDto userDto = userService.loggInUser(userLoginPOJO);
        if (userDto!=null) return new ResponseEntity<>(userDto, HttpStatus.ACCEPTED);
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PatchMapping("/edit/{id}")
    public ResponseEntity<User> editUser(@RequestBody UserDtoForRegister userDtoForRegister, @PathVariable Long id) {
        try {
            User updatedUser = userService.update(userDtoForRegister, id);
            return new ResponseEntity<>(updatedUser, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{userId}/add-favourite")
    public ResponseEntity<List<MedicalFacility>> addFavorite(@PathVariable Long userId, @RequestBody FavouriteMedicalFacilityPOJO facilityPOJO) {
        int added = userService.addFavorite(userId, facilityPOJO);

        if (added > 0) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/{userId}/remove-favourite")
    public ResponseEntity<List<MedicalFacility>> removeFavorite(@PathVariable Long userId, @RequestBody FavouriteMedicalFacilityPOJO facilityPOJO) {
        int removed = userService.removeFavorite(userId, facilityPOJO);

        if (removed > 0) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("{userId}/favorite-facilities")
    public ResponseEntity<List<MedicalFacilityListDto>> favoriteFacilities(@PathVariable Long userId) {
        return new ResponseEntity<>(userService.findFavoriteFacilitiesForUser(userId), HttpStatus.OK);
    }

    @GetMapping("/{userId}/image")
    public ResponseEntity<InputStreamResource> image(@PathVariable Long userId) throws IOException {
        InputStream in = userService.getUserImage(userId);

        if (in == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(new InputStreamResource(in));
    }

    @PostMapping("/{userId}/image/update")
    public ResponseEntity<Object> uploadFile(@PathVariable Long userId, @RequestParam("file") MultipartFile file) {
        if (!userService.userExists(userId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {
            String filename = this.saveImage(file);
            if (filename.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            if (!userService.setUserImage(userId, filename)) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{userId}/opinions")
    public ResponseEntity<List<OpinionWithMedicalFacilityDTO>> userOpinions(@PathVariable Long userId) {
        return new ResponseEntity<>(userService.getUserOpinions(userId), HttpStatus.OK);
    }

    @GetMapping("/{userId}/statistics")
    public ResponseEntity<UserStatisticsDTO> statistics(@PathVariable Long userId) {
        return new ResponseEntity<>(userService.getStatistics(userId), HttpStatus.OK);
    }

    protected String saveImage(MultipartFile file) throws IOException {
        String filename = this.buildFilename(file);
        Path fileNameAndPath = Paths.get(userService.UPLOAD_DIRECTORY, filename);

        Files.write(fileNameAndPath, file.getBytes());

        return filename;
    }

    protected String buildFilename(MultipartFile file) {
        return String.format("%s", RandomStringUtils.randomAlphanumeric(8)) +
                file.getOriginalFilename();
    }

}
