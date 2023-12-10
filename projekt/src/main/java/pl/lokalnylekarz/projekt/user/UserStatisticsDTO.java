package pl.lokalnylekarz.projekt.user;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class UserStatisticsDTO {
    protected int addedMedicalFacilities;
    protected int addedOpinions;
    protected int favoriteMedicalFacilities;
}
