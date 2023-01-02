package pl.lokalnylekarz.projekt.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.lokalnylekarz.projekt.api.Endpoint;
import pl.lokalnylekarz.projekt.medicalFacility.MedicalFacilityDto;

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
}
