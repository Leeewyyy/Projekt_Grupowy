package pl.lokalnylekarz.projekt.medicalFacility;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lokalnylekarz.projekt.enumeration.MedicalFacilityTypes;
import pl.lokalnylekarz.projekt.enumeration.Specialization;

@NoArgsConstructor
@Setter
@Getter
public class MedicalFacilityFilter {
    private MedicalFacilityTypes type;
    private Specialization specialization;
    private Double longitude;
    private Double latitude;
    private Double distance;
    private Boolean isNFZ;

    public boolean isEmpty() {
        return type == null && specialization == null && longitude == null && latitude == null && distance == null && isNFZ == null;
    }

    public boolean isFilterDistance() {
        return longitude != null && latitude != null && distance != null;
    }
}
