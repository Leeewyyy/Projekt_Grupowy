package pl.lokalnylekarz.projekt.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pl.lokalnylekarz.projekt.model.Opinion;

public interface OpinionRepository extends CrudRepository<Opinion, Long> {

    @Query("SELECT COUNT(o.id) FROM Opinion o WHERE o.medicalFacility.id = :medicalFacilityId")
    Long countByMedicalFacility(Long medicalFacilityId);

    @Query("SELECT SUM(o.rating) FROM Opinion o WHERE o.medicalFacility.id = :id")
    Long sumRatingsByMedicalFacility(Long id);
}
