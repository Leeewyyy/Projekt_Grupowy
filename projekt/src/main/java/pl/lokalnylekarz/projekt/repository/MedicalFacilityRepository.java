package pl.lokalnylekarz.projekt.repository;

import org.springframework.data.repository.CrudRepository;
import pl.lokalnylekarz.projekt.model.MedicalFacility;

public interface MedicalFacilityRepository extends CrudRepository<MedicalFacility, Long> {
}
