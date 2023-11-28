package pl.lokalnylekarz.projekt.medicalFacility;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lokalnylekarz.projekt.user.UserAddedByDto;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class MedicalFacilityForRegisterDto {
    private String name;
    private String type;
    private String address;
    private String phone;
    private String websiteUrl;
    private String description;
    private String nfzStatus;
    private Timestamp openFrom;
    private Timestamp openTo;
    private Double lat;
    private Double lon;
    private UserAddedByDto addedBy;
}
