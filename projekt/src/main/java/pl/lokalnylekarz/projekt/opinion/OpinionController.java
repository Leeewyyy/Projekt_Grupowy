package pl.lokalnylekarz.projekt.opinion;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lokalnylekarz.projekt.api.Endpoint;
import pl.lokalnylekarz.projekt.model.Opinion;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(Endpoint.OPINIONS_BASE_ROUTE)
public class OpinionController {

    private final OpinionService opinionService;

    @PostMapping("/add/{userId}/{facilityId}")
    public ResponseEntity<OpinionDto> addOpinion(@PathVariable Long userId, @PathVariable Long facilityId, @RequestBody OpinionFromFrontDto opinionFromFrontDto) {
        if (opinionFromFrontDto.getRating() == null) return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        if (opinionFromFrontDto.getText() == null || opinionFromFrontDto.getText().equals("")) return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

        Opinion opinion = opinionService.save(userId, facilityId, opinionFromFrontDto);
        return new ResponseEntity<>(opinionService.fromEntityToDto(opinion), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<OpinionDto>> getAll() {
        return new ResponseEntity<>(opinionService.getAll(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOpinion(@PathVariable Long id) {
        opinionService.deleteOpinion(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
