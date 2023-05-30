package pl.lokalnylekarz.projekt.specialist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pl.lokalnylekarz.projekt.enumeration.Specialization;

@Data
@AllArgsConstructor
@Builder
public class SpecialistDto {
    private Long id;
    private String firstname;
    private String lastname;
    private Specialization specialization;
}
