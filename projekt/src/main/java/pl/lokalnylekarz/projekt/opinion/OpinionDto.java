package pl.lokalnylekarz.projekt.opinion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pl.lokalnylekarz.projekt.enumeration.FacilityRatings;
import pl.lokalnylekarz.projekt.model.User;
import pl.lokalnylekarz.projekt.user.UserDto;

import java.sql.Timestamp;

@AllArgsConstructor
@Data
@Builder
public class OpinionDto {
    private Long id;
    private UserDto addedBy;
    private FacilityRatings rating;
    private String description;
    private Timestamp addedAt;

    public OpinionDto(
            Long id,
            FacilityRatings rating,
            String description,
            Timestamp addedAt
    ) {
        this.id = id;
        this.rating = rating;
        this.description = description;
        this.addedAt = addedAt;
    }
}
