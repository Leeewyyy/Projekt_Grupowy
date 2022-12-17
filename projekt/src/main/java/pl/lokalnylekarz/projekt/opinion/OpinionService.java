package pl.lokalnylekarz.projekt.opinion;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.lokalnylekarz.projekt.model.Opinion;
import pl.lokalnylekarz.projekt.user.UserService;

@RequiredArgsConstructor
@Service
public class OpinionService {

    public static OpinionDto fromEntityToDto(Opinion opinion) {
        return new OpinionDto(
                opinion.getId(),
                UserService.fromEntityToDto(opinion.getAddedBy()),
                opinion.getRating(),
                opinion.getDescription(),
                opinion.getAddedAt()
        );
    }

    public static OpinionDto forUser(Opinion opinion) {
        return new OpinionDto(
                opinion.getId(),
                opinion.getRating(),
                opinion.getDescription(),
                opinion.getAddedAt()
        );
    }
}
