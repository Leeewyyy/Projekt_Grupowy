package pl.lokalnylekarz.projekt.opinion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pl.lokalnylekarz.projekt.medicalFacility.MedicalFacilityDto;
import pl.lokalnylekarz.projekt.medicalFacility.MedicalFacilityListDto;

import java.sql.Timestamp;

@AllArgsConstructor
@Data
@Builder
public class OpinionWithMedicalFacilityDTO {
    private Long id;
    private Integer rating;
    private String description;
    private MedicalFacilityListDto medicalFacility;
    private Timestamp addedAt;
}