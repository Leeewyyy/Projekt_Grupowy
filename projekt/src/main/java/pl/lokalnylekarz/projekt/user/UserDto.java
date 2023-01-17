package pl.lokalnylekarz.projekt.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pl.lokalnylekarz.projekt.medicalFacility.MedicalFacilityDto;
import pl.lokalnylekarz.projekt.opinion.OpinionDto;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String login;
    private String firstname;
    private String lastname;
    private String email;
    private Timestamp registrationDate;
    private List<OpinionDto> opinions;
    private List<MedicalFacilityDto> addedMedicalFacilities;
    private List<MedicalFacilityDto> favoriteMedicalFacilities;

    public UserDto(
            Long id,
            String login,
            String firstname,
            String lastname,
            String email,
            Timestamp registrationDate
    ) {
        this.id = id;
        this.login = login;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.registrationDate = registrationDate;
    }
}
