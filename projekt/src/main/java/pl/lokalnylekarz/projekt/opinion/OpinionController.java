package pl.lokalnylekarz.projekt.opinion;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lokalnylekarz.projekt.api.Endpoint;
import pl.lokalnylekarz.projekt.model.Opinion;

@RequiredArgsConstructor
@RestController
@RequestMapping(Endpoint.OPINIONS_BASE_ROUTE)
public class OpinionController {

    private final OpinionService opinionService;

    @PostMapping("/add/{userId}/{facilityId}")
    public ResponseEntity<Opinion> addOpinion(@PathVariable Long userId, @PathVariable Long facilityId, @RequestBody OpinionFromFrontDto opinionFromFrontDto) {
        Opinion dto = opinionService.save(userId, facilityId, opinionFromFrontDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
}
