package pl.lokalnylekarz.projekt.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lokalnylekarz.projekt.api.Endpoint;
import pl.lokalnylekarz.projekt.model.MedicalFacility;
import pl.lokalnylekarz.projekt.model.User;

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
    public ResponseEntity<List<MedicalFacility>> changeFavorite(@PathVariable Long userId, @PathVariable Long facilityId) {
        User user = userService.changeFavorite(userId, facilityId);
        return new ResponseEntity<>(user.getFavoriteFacilities(), HttpStatus.CREATED);
    }

    @GetMapping("/favoriteFacilities/{userId}")
    public ResponseEntity<List<MedicalFacility>> favoriteFacilities(@PathVariable Long userId) {
        return new ResponseEntity<>(userService.findFavoriteFacilitiesForUser(userId), HttpStatus.OK);
    }
}
