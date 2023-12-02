package pl.lokalnylekarz.projekt;

import org.junit.jupiter.api.Test;
import pl.lokalnylekarz.projekt.dataTypes.Location;
import pl.lokalnylekarz.projekt.medicalFacility.MedicalFacilityListDto;
import pl.lokalnylekarz.projekt.opinion.OpinionDto;
import pl.lokalnylekarz.projekt.user.UserDto;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.lokalnylekarz.projekt.enumeration.MedicalFacilityTypes.SZPITAL;
import static pl.lokalnylekarz.projekt.enumeration.NfzStatuses.PARTIAL;

public class UserDtoTest {

    @Test
    public void testUserDtoConstructor() {
        // Given
        Long id = 1L;
        String fullName = "John Doe";
        String email = "john.doe@example.com";
        String imageUrl = "https://example.com/image.jpg";
        Timestamp registrationDate = new Timestamp(System.currentTimeMillis());
        List<OpinionDto> opinions = Arrays.asList(new OpinionDto(1L, 1, "Description", new Timestamp(1701534062)));
        List<MedicalFacilityListDto> addedMedicalFacilities = Arrays.asList(new MedicalFacilityListDto(/* set medical facility properties */));
        List<MedicalFacilityListDto> favoriteMedicalFacilities = Arrays.asList(new MedicalFacilityListDto(/* set medical facility properties */));
        String role = "USER";
        LocalDate verificationDate = LocalDate.now();

        // When
        UserDto userDto = new UserDto(
                id, fullName, email, imageUrl, registrationDate, opinions,
                addedMedicalFacilities, favoriteMedicalFacilities, role, verificationDate
        );

        // Then
        assertEquals(id, userDto.getId());
        assertEquals(fullName, userDto.getFullName());
        assertEquals(email, userDto.getEmail());
        assertEquals(imageUrl, userDto.getImageUrl());
        assertEquals(registrationDate, userDto.getRegistrationDate());
        assertEquals(opinions, userDto.getOpinions());
        assertEquals(addedMedicalFacilities, userDto.getAddedMedicalFacilities());
        assertEquals(favoriteMedicalFacilities, userDto.getFavoriteMedicalFacilities());
        assertEquals(role, userDto.getRole());
        assertEquals(verificationDate, userDto.getVerificationDate());
    }

    @Test
    public void testUserDtoBuilder() {
        // Given
        Long id = 1L;
        String fullName = "John Doe";
        String email = "john.doe@example.com";
        String imageUrl = "https://example.com/image.jpg";
        Timestamp registrationDate = new Timestamp(System.currentTimeMillis());
        List<OpinionDto> opinions = Arrays.asList(new OpinionDto(1L, 1, "Description", new Timestamp(1701534062)));
        List<MedicalFacilityListDto> addedMedicalFacilities = Arrays.asList(new MedicalFacilityListDto(
                1L, "Name", SZPITAL, "address", imageUrl, "Phone", imageUrl, PARTIAL, 1f, 1L,
                LocalTime.now(), LocalTime.now(), new Location(1d, 1d),
                new Date(1701534062), new ArrayList<String>()
        ));
        List<MedicalFacilityListDto> favoriteMedicalFacilities =  Arrays.asList(new MedicalFacilityListDto(
                1L, "Name", SZPITAL, "address", imageUrl, "Phone", imageUrl, PARTIAL, 1f, 1L,
                LocalTime.now(), LocalTime.now(), new Location(1d, 1d),
                new Date(1701534062), new ArrayList<String>()
        ));
        String role = "USER";
        LocalDate verificationDate = LocalDate.now();

        // When
        UserDto userDto = UserDto.builder()
                .id(id)
                .fullName(fullName)
                .email(email)
                .imageUrl(imageUrl)
                .registrationDate(registrationDate)
                .opinions(opinions)
                .addedMedicalFacilities(addedMedicalFacilities)
                .favoriteMedicalFacilities(favoriteMedicalFacilities)
                .role(role)
                .verificationDate(verificationDate)
                .build();

        // Then
        assertEquals(id, userDto.getId());
        assertEquals(fullName, userDto.getFullName());
        assertEquals(email, userDto.getEmail());
        assertEquals(imageUrl, userDto.getImageUrl());
        assertEquals(registrationDate, userDto.getRegistrationDate());
        assertEquals(opinions, userDto.getOpinions());
        assertEquals(addedMedicalFacilities, userDto.getAddedMedicalFacilities());
        assertEquals(favoriteMedicalFacilities, userDto.getFavoriteMedicalFacilities());
        assertEquals(role, userDto.getRole());
        assertEquals(verificationDate, userDto.getVerificationDate());
    }

    @Test
    public void testUserDtoProperties() {
        // Given
        Long id = 1L;
        String fullName = "John Doe";
        String email = "john.doe@example.com";
        String imageUrl = "https://example.com/image.jpg";
        Timestamp registrationDate = new Timestamp(System.currentTimeMillis());
        List<OpinionDto> opinions = Arrays.asList(new OpinionDto(1L, 1, "Description", new Timestamp(1701534062)));
        List<MedicalFacilityListDto> addedMedicalFacilities = Arrays.asList(new MedicalFacilityListDto(/* set medical facility properties */));
        List<MedicalFacilityListDto> favoriteMedicalFacilities = Arrays.asList(new MedicalFacilityListDto(/* set medical facility properties */));
        String role = "USER";
        LocalDate verificationDate = LocalDate.now();

        // When
        UserDto userDto = UserDto.builder().build();

        // Then (Testing setters)
        userDto.setId(id);
        userDto.setFullName(fullName);
        userDto.setEmail(email);
        userDto.setImageUrl(imageUrl);
        userDto.setRegistrationDate(registrationDate);
        userDto.setOpinions(opinions);
        userDto.setAddedMedicalFacilities(addedMedicalFacilities);
        userDto.setFavoriteMedicalFacilities(favoriteMedicalFacilities);
        userDto.setRole(role);
        userDto.setVerificationDate(verificationDate);

        // Assertions (Testing getters)
        assertEquals(id, userDto.getId());
        assertEquals(fullName, userDto.getFullName());
        assertEquals(email, userDto.getEmail());
        assertEquals(imageUrl, userDto.getImageUrl());
        assertEquals(registrationDate, userDto.getRegistrationDate());
        assertEquals(opinions, userDto.getOpinions());
        assertEquals(addedMedicalFacilities, userDto.getAddedMedicalFacilities());
        assertEquals(favoriteMedicalFacilities, userDto.getFavoriteMedicalFacilities());
        assertEquals(role, userDto.getRole());
        assertEquals(verificationDate, userDto.getVerificationDate());
    }
}
