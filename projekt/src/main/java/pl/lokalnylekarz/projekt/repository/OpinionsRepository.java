package pl.lokalnylekarz.projekt.repository;

import org.springframework.data.repository.CrudRepository;
import pl.lokalnylekarz.projekt.model.Opinion;

public interface OpinionsRepository extends CrudRepository<Opinion, Long> {
}
