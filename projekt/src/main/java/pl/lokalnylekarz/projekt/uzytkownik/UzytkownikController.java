package pl.lokalnylekarz.projekt.uzytkownik;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UzytkownikController {

    private final UzytkownikService service;
}
