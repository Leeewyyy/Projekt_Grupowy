package pl.lokalnylekarz.projekt.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pl.lokalnylekarz.projekt.medicalFacility.MedicalFacilityDto;
import pl.lokalnylekarz.projekt.model.Opinion;
import pl.lokalnylekarz.projekt.opinion.OpinionDto;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String imageUrl;
    private Timestamp registrationDate;
    private List<OpinionDto> opinions;
    private List<MedicalFacilityDto> addedMedicalFacilities;
    private List<MedicalFacilityDto> favoriteMedicalFacilities;

    public UserDto(
            Long id,
            String firstname,
            String lastname,
            String email,
            String imageUrl,
            List<OpinionDto> opinions,
            Timestamp registrationDate
    ) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.imageUrl = imageUrl;
        this.opinions = opinions;
        this.registrationDate = registrationDate;
    }
}
