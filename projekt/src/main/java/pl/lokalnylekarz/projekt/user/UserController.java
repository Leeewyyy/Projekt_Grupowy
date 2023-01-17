package pl.lokalnylekarz.projekt.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lokalnylekarz.projekt.api.Endpoint;
import pl.lokalnylekarz.projekt.model.MedicalFacility;
import pl.lokalnylekarz.projekt.model.User;
import pl.lokalnylekarz.projekt.pojo.FavouriteMedicalFacilityPOJO;
import pl.lokalnylekarz.projekt.pojo.UserLoginPOJO;

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

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody UserLoginPOJO userLoginPOJO) {
        return (userService.checkUser(userLoginPOJO)) ? new ResponseEntity<>(HttpStatus.ACCEPTED) : new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PatchMapping("/edit/{id}")
    public ResponseEntity<User> editUser(@RequestBody UserDtoForRegister userDtoForRegister, @PathVariable Long id) {
        User updatedUser = userService.update(userDtoForRegister, id);
        return new ResponseEntity<>(updatedUser, HttpStatus.CREATED);
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

    @GetMapping("/favoriteFacilities/{userId}")
    public ResponseEntity<List<MedicalFacility>> favoriteFacilities(@PathVariable Long userId) {
        return new ResponseEntity<>(userService.findFavoriteFacilitiesForUser(userId), HttpStatus.OK);
    }
}
