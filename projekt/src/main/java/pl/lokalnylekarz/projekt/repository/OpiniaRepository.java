package pl.lokalnylekarz.projekt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lokalnylekarz.projekt.model.Opinia;

public interface OpiniaRepository extends JpaRepository<Opinia, Long> {
}
