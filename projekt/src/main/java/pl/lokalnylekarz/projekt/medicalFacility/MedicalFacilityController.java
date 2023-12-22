package pl.lokalnylekarz.projekt.medicalFacility;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lokalnylekarz.projekt.api.Endpoint;
import pl.lokalnylekarz.projekt.enumeration.MedicalFacilityTypes;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(Endpoint.MEDICAL_FACILITIES_BASE_ROUTE)
public class MedicalFacilityController {

    private final MedicalFacilityService service;

    @GetMapping
    @ResponseBody
    @JsonIgnoreProperties("specialist")
    public List<MedicalFacilityListDto> getMedicalFacilities(MedicalFacilityFilter filters) {
        if (filters.getAddedBy() != null) return service.getAllAddedByUser(filters.getAddedBy());
        else return filters.isEmpty() ? service.getAll() : service.getAll(filters);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<MedicalFacilityDto> getMedicalFacilityDetails(@PathVariable(value = "id") Long id) {
        try {
            return ResponseEntity.ok(service.get(id));
        }    catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/types")
    @ResponseBody
    public List<MedicalFacilityTypes> getMedicalFacilityTypes() {
        return List.of(MedicalFacilityTypes.values());
    }

    @PostMapping()
    public ResponseEntity<MedicalFacilityDto> createMedicalFacility(@RequestBody MedicalFacilityForRegisterDto medicalFacilityForRegisterDto) {
        try {
            return ResponseEntity.ok(service.create(medicalFacilityForRegisterDto));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MedicalFacilityDto> editMedicalFacility(@PathVariable(value = "id") Long id, MedicalFacilityForRegisterDto medicalFacilityForRegisterDto) {
        try {
            return ResponseEntity.ok(service.edit(id, medicalFacilityForRegisterDto));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteMedicalFacility(@PathVariable(value = "id") Long id) {
        try {
            service.delete(id);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
