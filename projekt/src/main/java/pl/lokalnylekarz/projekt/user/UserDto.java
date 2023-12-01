package pl.lokalnylekarz.projekt.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pl.lokalnylekarz.projekt.medicalFacility.MedicalFacilityDto;
import pl.lokalnylekarz.projekt.medicalFacility.MedicalFacilityListDto;
import pl.lokalnylekarz.projekt.model.Opinion;
import pl.lokalnylekarz.projekt.opinion.OpinionDto;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String fullName;
    private String email;
    private String imageUrl;
    private Timestamp registrationDate;
    private List<OpinionDto> opinions;
    private List<MedicalFacilityListDto> addedMedicalFacilities;
    private List<MedicalFacilityListDto> favoriteMedicalFacilities;
    private String role;
    private Timestamp verificationDate;

    public UserDto(
            Long id,
            String fullName,
            String email,
            String imageUrl,
            List<OpinionDto> opinions,
            Timestamp registrationDate
    ) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.imageUrl = imageUrl;
        this.opinions = opinions;
        this.registrationDate = registrationDate;
    }
}
