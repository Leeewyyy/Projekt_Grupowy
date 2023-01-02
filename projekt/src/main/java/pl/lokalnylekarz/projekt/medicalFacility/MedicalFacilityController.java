package pl.lokalnylekarz.projekt.medicalFacility;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.mapper.Mapper;
import org.springframework.web.bind.annotation.*;
import pl.lokalnylekarz.projekt.api.Endpoint;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(Endpoint.MEDICAL_FACILITIES_BASE_ROUTE)
public class MedicalFacilityController {

    private final MedicalFacilityService service;

    @GetMapping()
    @ResponseBody
    @JsonIgnoreProperties("specialist")
    public List<MedicalFacilityDto> getMedicalFacilities() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public MedicalFacilityDto getMedicalFacilityDetails(@PathVariable(value = "id") Long id) {
        return service.get(id);
    }
}
