package pl.lokalnylekarz.projekt.specialist;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SpecialistController {

    private final SpecialistService service;
}
