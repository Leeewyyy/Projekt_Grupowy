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
import pl.lokalnylekarz.projekt.model.MedicalFacility;
import pl.lokalnylekarz.projekt.model.User;

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
    public UserDto getUserDetails(@PathVariable(value = "id") Long id) {
        return userService.get(id);
    }

    @PostMapping("/register")
    public ResponseEntity<User> saveUser(@RequestBody UserDtoForRegister userDtoForRegister) {
        User dto = userService.save(userDtoForRegister);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PostMapping("/login/{email}/{password}")
    public ResponseEntity<User> login(@PathVariable("email") String email, @PathVariable("password") String password) {
        if (userService.checkUser(email, password)) return new ResponseEntity<>(HttpStatus.ACCEPTED);
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PatchMapping("/edit/{id}")
    public ResponseEntity<User> editUser(@RequestBody UserDtoForRegister userDtoForRegister, @PathVariable Long id) {
        User updatedUser = userService.update(userDtoForRegister, id);
        return new ResponseEntity<>(updatedUser, HttpStatus.CREATED);
    }

    @PostMapping("/changeFavorite/{userId}/{facilityId}") //ŹLE ZROBIONA METODA W SERVICE
    public ResponseEntity<List<MedicalFacility>> changeFavorite(
            @PathVariable Long userId,
            @PathVariable Long facilityId
    ) {
        User user = userService.changeFavorite(userId, facilityId);
        return new ResponseEntity<>(user.getFavoriteFacilities(), HttpStatus.CREATED);
    }

    @GetMapping("/favoriteFacilities/{userId}")
    public ResponseEntity<List<MedicalFacility>> favoriteFacilities(@PathVariable Long userId) {
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
