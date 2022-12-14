package pl.lokalnylekarz.projekt.repository;

import org.springframework.data.repository.CrudRepository;
import pl.lokalnylekarz.projekt.model.Specialist;

public interface SpecialistRepository extends CrudRepository<Specialist, Long> {
}
