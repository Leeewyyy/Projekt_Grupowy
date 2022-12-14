package pl.lokalnylekarz.projekt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lokalnylekarz.projekt.model.Specjalista;

public interface SpecjalistaRepository extends JpaRepository<Specjalista, Long> {
}
