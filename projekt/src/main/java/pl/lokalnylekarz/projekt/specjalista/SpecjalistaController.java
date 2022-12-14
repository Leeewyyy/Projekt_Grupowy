package pl.lokalnylekarz.projekt.specjalista;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SpecjalistaController {

    private final SpecjalistaService service;
}
