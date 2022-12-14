package pl.lokalnylekarz.projekt.opinia;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class OpiniaController {

    private final OpiniaService service;
}
