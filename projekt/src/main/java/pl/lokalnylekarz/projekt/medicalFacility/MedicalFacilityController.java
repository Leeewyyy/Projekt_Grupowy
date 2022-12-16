package pl.lokalnylekarz.projekt.medicalFacility;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pl.lokalnylekarz.projekt.api.Endpoint;
import pl.lokalnylekarz.projekt.model.MedicalFacility;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class MedicalFacilityController {

    private final MedicalFacilityService service;


    @GetMapping(Endpoint.GET_ALL_MEDICAL_FACILITIES)
    @ResponseBody
    public List<MedicalFacility> getMedicalFacilities(){
        return service.getAll();
    }
}
