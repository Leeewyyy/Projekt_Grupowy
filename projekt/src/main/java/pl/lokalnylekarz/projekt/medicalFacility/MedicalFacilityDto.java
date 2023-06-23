package pl.lokalnylekarz.projekt.medicalFacility;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lokalnylekarz.projekt.dataTypes.Image;
import pl.lokalnylekarz.projekt.dataTypes.Location;
import pl.lokalnylekarz.projekt.enumeration.MedicalFacilityTypes;
import pl.lokalnylekarz.projekt.enumeration.NfzStatuses;
import pl.lokalnylekarz.projekt.opinion.OpinionDto;
import pl.lokalnylekarz.projekt.specialist.SpecialistDto;
import pl.lokalnylekarz.projekt.user.UserAddedByDto;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalFacilityDto {
    private Long id;
    private String name;
    private MedicalFacilityTypes type;
    private String address;
    private String imageUrl;
    private List<Image> images;
    private String phone;
    private String websiteUrl;
    private String description;
    private NfzStatuses nfzStatus;
    private Float rating;
    private Long ratingCount;
    private Timestamp openFrom;
    private Timestamp openTo;
    private Location location;
    private Date addedAt;
    private List<SpecialistDto> specialists;
    private UserAddedByDto addedBy;
    private List<OpinionDto> opinions;
}
