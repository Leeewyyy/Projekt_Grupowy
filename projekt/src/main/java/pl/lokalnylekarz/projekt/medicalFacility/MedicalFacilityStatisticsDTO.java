package pl.lokalnylekarz.projekt.medicalFacility;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalFacilityStatisticsDTO {
    private Long id;
    private String name;
    private String imageUrl;
    private int hits;
}
