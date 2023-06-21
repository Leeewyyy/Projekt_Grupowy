package pl.lokalnylekarz.projekt.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pl.lokalnylekarz.projekt.model.MedicalFacility;

import java.util.List;

public interface MedicalFacilityRepository extends CrudRepository<MedicalFacility, Long>, JpaSpecificationExecutor<MedicalFacility> {

    @Query("select distinct M from MedicalFacility M left join fetch M.favoriteFor F")
    List<MedicalFacility> findAll();

    List<MedicalFacility> findByAddressContainingIgnoreCase(String phrase);

    List<MedicalFacility> findByNameContainingIgnoreCase(String phrase);
}
