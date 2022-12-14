package pl.lokalnylekarz.projekt.medicalFacility;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pl.lokalnylekarz.projekt.dataTypes.Location;
import pl.lokalnylekarz.projekt.enumeration.MedicalFacilityTypes;
import pl.lokalnylekarz.projekt.opinion.OpinionDto;
import pl.lokalnylekarz.projekt.specialist.SpecialistDto;
import pl.lokalnylekarz.projekt.user.UserDto;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class MedicalFacilityDto {
    private Long id;
    private String name;
    private MedicalFacilityTypes type;
    private String address;
    private String imageUrl;
    private String phone;
    private String websiteUrl;
    private String description;
    private Boolean isNFZ;
    private Integer rating;
    private Timestamp openFrom;
    private Timestamp openTo;
    private Location location;
    private Date addedAt;
    private List<SpecialistDto> specialist;
    private UserDto addedBy;
    private List<OpinionDto> opinions;
    private List<UserDto> favoriteFor;

    public MedicalFacilityDto(
            Long id,
            String name,
            MedicalFacilityTypes type,
            String address,
            String phone,
            String websiteUrl,
            Boolean isNFZ,
            Integer rating,
            Timestamp openFrom,
            Timestamp openTo,
            Location location,
            Date addedAt,
            List<UserDto> favoriteFor) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.address = address;
        this.phone = phone;
        this.websiteUrl = websiteUrl;
        this.isNFZ = isNFZ;
        this.rating = rating;
        this.openFrom = openFrom;
        this.openTo = openTo;
        this.location = location;
        this.addedAt = addedAt;
        this.favoriteFor = favoriteFor;
    }
}
