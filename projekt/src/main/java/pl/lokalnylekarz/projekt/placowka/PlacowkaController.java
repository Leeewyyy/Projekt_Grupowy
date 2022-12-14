package pl.lokalnylekarz.projekt.placowka;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PlacowkaController {

    private final PlacowkaService service;


}
