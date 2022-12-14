package pl.lokalnylekarz.projekt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lokalnylekarz.projekt.model.Placowka;

public interface PlacowkaRepository extends JpaRepository<Placowka, Long> {
}
