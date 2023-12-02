package pl.lokalnylekarz.projekt.medicalFacility;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lokalnylekarz.projekt.api.Endpoint;
import pl.lokalnylekarz.projekt.enumeration.MedicalFacilityTypes;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(Endpoint.MEDICAL_FACILITIES_BASE_ROUTE)
public class MedicalFacilityController {

    private final MedicalFacilityService service;

    @GetMapping
    @ResponseBody
    @JsonIgnoreProperties("specialist")
    public List<MedicalFacilityListDto> getMedicalFacilities(@RequestBody(required = false) MedicalFacilityFilter filters,
                                                             @RequestParam(name = "addedBy", required = false) Long userId) {
        if (userId != null) return service.getAllAddedByUser(userId);
        else return (filters == null || filters.isEmpty()) ? service.getAll() : service.getAll(filters);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public MedicalFacilityDto getMedicalFacilityDetails(@PathVariable(value = "id") Long id) {
        return service.get(id);
    }

    @GetMapping("/types")
    @ResponseBody
    public List<MedicalFacilityTypes> getMedicalFacilityTypes() {
        return List.of(MedicalFacilityTypes.values());
    }

    @PostMapping()
    public MedicalFacilityDto createMedicalFacility(@RequestBody MedicalFacilityForRegisterDto medicalFacilityForRegisterDto) {
        return service.create(medicalFacilityForRegisterDto);
    }

    @PatchMapping("/{id}")
    public MedicalFacilityDto editMedicalFacility(@PathVariable(value = "id") Long id, @RequestBody MedicalFacilityForRegisterDto medicalFacilityForRegisterDto) {
        return service.edit(id, medicalFacilityForRegisterDto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteMedicalFacility(@PathVariable(value = "id") Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
