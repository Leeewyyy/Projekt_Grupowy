package pl.lokalnylekarz.projekt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.lokalnylekarz.projekt.medicalFacility.MedicalFacilityService;
import pl.lokalnylekarz.projekt.model.User;
import pl.lokalnylekarz.projekt.opinion.OpinionDto;
import pl.lokalnylekarz.projekt.pojo.FavouriteMedicalFacilityPOJO;
import pl.lokalnylekarz.projekt.pojo.UserLoginPOJO;
import pl.lokalnylekarz.projekt.user.UserController;
import pl.lokalnylekarz.projekt.user.UserDto;
import pl.lokalnylekarz.projekt.user.UserDtoForRegister;
import pl.lokalnylekarz.projekt.user.UserService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebMvcTest(UserController.class)
public class UserControllerApiTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void testUserOpinionsEndpoint() throws Exception {
        Long userId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.get("/users/{userId}/opinions", userId)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetUsersEndpoint() throws Exception {
        List<UserDto> mockUserList = Arrays.asList(
                new UserDto(1L, "User1", "mail", "url", new ArrayList<OpinionDto>(), new Timestamp(1701534507)),
                new UserDto(2L, "User2", "mail", "url", new ArrayList<OpinionDto>(), new Timestamp(1701534507))
        );

        Mockito.when(userService.getAll()).thenReturn(mockUserList);

        mockMvc.perform(MockMvcRequestBuilders.get("/users")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(mockUserList.size()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].fullName").value("User1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].fullName").value("User2"));
    }

    @Test
    public void testRegisterUsersEndpoint() throws Exception {
        UserDtoForRegister userDto = new UserDtoForRegister("", "email", "password", "role");

        Mockito.when(userService.save(Mockito.any(UserDtoForRegister.class))).thenReturn(new User());

        mockMvc.perform(MockMvcRequestBuilders.post("/users/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(userDto)))
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable());

        userDto.setFullName("name");
        userDto.setEmail("");

        mockMvc.perform(MockMvcRequestBuilders.post("/users/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(userDto)))
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable());

        userDto.setEmail("email");
        userDto.setPassword("");

        mockMvc.perform(MockMvcRequestBuilders.post("/users/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(userDto)))
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable());

        userDto.setPassword("password");
        userDto.setRole("");

        mockMvc.perform(MockMvcRequestBuilders.post("/users/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(userDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        Mockito.when(userService.save(Mockito.any(UserDtoForRegister.class))).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/users/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(userDto)))
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable());
    }

    @Test
    public void testLoginEndpoint_ValidCredentials() throws Exception {
        UserLoginPOJO loginCredentials = new UserLoginPOJO();
        loginCredentials.setEmail("test@example.com");
        loginCredentials.setPassword("password123");

        Mockito.when(userService.loggInUser(Mockito.any(UserLoginPOJO.class))).thenReturn(new UserDto(
                1L,
                "Name",
                "test@example.com",
                "url",
                new ArrayList<OpinionDto>(),
                new Timestamp(1701534062)
        ));

        mockMvc.perform(MockMvcRequestBuilders.post("/users/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(loginCredentials)))
                .andExpect(MockMvcResultMatchers.status().isAccepted());
    }


    @Test
    public void testLoginEndpoint_InvalidCredentials() throws Exception {
        UserLoginPOJO loginCredentials = new UserLoginPOJO();
        loginCredentials.setEmail("test@example.com");
        loginCredentials.setPassword("password123");

        Mockito.when(userService.loggInUser(Mockito.any(UserLoginPOJO.class))).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/users/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(loginCredentials)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    public void testEditEndopint() throws Exception {
        Long userId = 1L;

        UserDtoForRegister userDto = new UserDtoForRegister("Name", "email", "password", "role");

        Mockito.when(userService.update(Mockito.any(UserDtoForRegister.class), Mockito.eq(userId)))
                .thenReturn(new User());

        mockMvc.perform(MockMvcRequestBuilders.patch("/users/edit/{id}", userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(userDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testAddFavoriteEndpoint_Success() throws Exception {
        Long userId = 1L;
        FavouriteMedicalFacilityPOJO facilityPOJO = new FavouriteMedicalFacilityPOJO();
        facilityPOJO.setFacilitiesId(new ArrayList<Long>(){
            {
                add(1L);
                add(2L);
                add(3L);
                add(4L);
            }
        });

        Mockito.when(userService.addFavorite(Mockito.eq(userId), Mockito.any(FavouriteMedicalFacilityPOJO.class)))
                .thenReturn(1);

        mockMvc.perform(MockMvcRequestBuilders.post("/users/{userId}/add-favourite", userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(facilityPOJO)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testAddFavoriteEndpoint_Failure() throws Exception {
        Long userId = 1L;
        FavouriteMedicalFacilityPOJO facilityPOJO = new FavouriteMedicalFacilityPOJO();
        facilityPOJO.setFacilitiesId(new ArrayList<Long>(){
            {
                add(1L);
                add(2L);
                add(3L);
                add(4L);
            }
        });

        Mockito.when(userService.addFavorite(Mockito.eq(userId), Mockito.any(FavouriteMedicalFacilityPOJO.class)))
                .thenReturn(0);

        mockMvc.perform(MockMvcRequestBuilders.post("/users/{userId}/add-favourite", userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(facilityPOJO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
