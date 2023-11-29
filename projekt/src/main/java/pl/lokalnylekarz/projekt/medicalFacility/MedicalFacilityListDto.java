package pl.lokalnylekarz.projekt.medicalFacility;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lokalnylekarz.projekt.dataTypes.Location;
import pl.lokalnylekarz.projekt.enumeration.MedicalFacilityTypes;
import pl.lokalnylekarz.projekt.enumeration.NfzStatuses;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.List;

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
    private NfzStatuses nfzStatus;
    private Float rating;
    private Long ratingCount;
    private LocalTime openFrom;
    private LocalTime openTo;
    private Location location;
    private Date addedAt;
    private List<String> specialists;
}
