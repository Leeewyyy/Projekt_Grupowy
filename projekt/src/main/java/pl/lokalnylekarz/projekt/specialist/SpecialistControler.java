package pl.lokalnylekarz.projekt.specialist;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pl.lokalnylekarz.projekt.api.Endpoint;
import pl.lokalnylekarz.projekt.enumeration.Specialization;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(Endpoint.SPECIALISTS)
public class SpecialistControler {

    @GetMapping("/types")
    @ResponseBody
    public List<Specialization> getSpecialistsTypes() { return List.of(Specialization.values()); }
}
