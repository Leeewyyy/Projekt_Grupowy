package pl.lokalnylekarz.projekt.opinion;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lokalnylekarz.projekt.api.Endpoint;
import pl.lokalnylekarz.projekt.medicalFacility.MedicalFacilityService;
import pl.lokalnylekarz.projekt.model.Opinion;

@RequiredArgsConstructor
@RestController
@RequestMapping(Endpoint.OPINIONS_BASE_ROUTE)
public class OpinionController {

    private final OpinionService opinionService;

    @PostMapping("/add/{userId}/{facilityId}")
    public ResponseEntity<OpinionDto> addOpinion(@PathVariable Long userId, @PathVariable Long facilityId, @RequestBody OpinionFromFrontDto opinionFromFrontDto) {
        Opinion opinion = opinionService.save(userId, facilityId, opinionFromFrontDto);
        return new ResponseEntity<>(OpinionService.fromEntityToDto(opinion), HttpStatus.CREATED);
    }
}
