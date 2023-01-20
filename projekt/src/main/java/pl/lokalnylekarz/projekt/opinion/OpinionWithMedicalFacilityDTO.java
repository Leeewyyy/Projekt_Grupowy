package pl.lokalnylekarz.projekt.opinion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pl.lokalnylekarz.projekt.medicalFacility.MedicalFacilityDto;

import java.sql.Timestamp;

@AllArgsConstructor
@Data
@Builder
public class OpinionWithMedicalFacilityDTO {
    private Long id;
    private Integer rating;
    private String description;
    private MedicalFacilityDto medicalFacility;
    private Timestamp addedAt;
}