package pl.lokalnylekarz.projekt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lokalnylekarz.projekt.model.Uzytkownik;

public interface UzytkownikRepository extends JpaRepository<Uzytkownik, Long> {
}
