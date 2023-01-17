package pl.lokalnylekarz.projekt.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pl.lokalnylekarz.projekt.enumeration.MedicalFacilityTypes;
import pl.lokalnylekarz.projekt.enumeration.Specialization;
import pl.lokalnylekarz.projekt.model.MedicalFacility;

import java.util.List;

public interface MedicalFacilityRepository extends CrudRepository<MedicalFacility, Long> {

    @Query("select distinct M from MedicalFacility M left join fetch M.favoriteFor F")
    List<MedicalFacility> findAll();

    List<MedicalFacility> findByTypeAndSpecialistSpecialization(MedicalFacilityTypes types, Specialization specialization);
    List<MedicalFacility> findByType(MedicalFacilityTypes types);
    List<MedicalFacility> findBySpecialistSpecialization(Specialization specialization);
}
