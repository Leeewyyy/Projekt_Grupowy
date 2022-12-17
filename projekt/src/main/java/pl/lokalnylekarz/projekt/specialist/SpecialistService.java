package pl.lokalnylekarz.projekt.specialist;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.lokalnylekarz.projekt.model.Specialist;

@RequiredArgsConstructor
@Service
public class SpecialistService {

    public static SpecialistDto fromEntityToDto(Specialist specialist) {
        return new SpecialistDto(
                specialist.getId(),
                specialist.getFirstname(),
                specialist.getLastname(),
                specialist.getSpecialization()
        );
    }
}
