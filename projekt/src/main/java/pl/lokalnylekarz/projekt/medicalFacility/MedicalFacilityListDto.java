package pl.lokalnylekarz.projekt.medicalFacility;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lokalnylekarz.projekt.dataTypes.Location;
import pl.lokalnylekarz.projekt.enumeration.MedicalFacilityTypes;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalFacilityListDto {
    private Long id;
    private String name;
    private MedicalFacilityTypes type;
    private String address;
    private String imageUrl;
    private String phone;
    private String websiteUrl;
    private Boolean isNFZ;
    private Float rating;
    private Long ratingCount;
    private Timestamp openFrom;
    private Timestamp openTo;
    private Location location;
    private Date addedAt;
}
