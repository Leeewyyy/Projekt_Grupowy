package pl.lokalnylekarz.projekt.medicalFacility;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lokalnylekarz.projekt.enumeration.MedicalFacilityTypes;
import pl.lokalnylekarz.projekt.enumeration.NfzStatuses;
import pl.lokalnylekarz.projekt.enumeration.Specialization;

import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class MedicalFacilityFilter {
    private MedicalFacilityTypes type;
    private Specialization specialization;
    private Double longitude;
    private Double latitude;
    private Double distance;
    private List<String> nfzStatus;
    private String search;

    public boolean isEmpty() {
        return type == null && specialization == null && longitude == null && latitude == null && distance == null && nfzStatus == null && search == null;
    }

    public boolean isFilterDistance() {
        return (longitude != null && latitude != null && distance != null);
    }

    public boolean isSearch() {
        return search != null;
    }
}
