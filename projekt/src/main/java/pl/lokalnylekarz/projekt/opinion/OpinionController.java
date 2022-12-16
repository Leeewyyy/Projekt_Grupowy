package pl.lokalnylekarz.projekt.opinion;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class OpinionController {

    private final OpinionService service;
}
