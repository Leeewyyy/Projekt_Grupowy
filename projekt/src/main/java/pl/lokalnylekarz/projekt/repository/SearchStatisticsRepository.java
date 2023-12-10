package pl.lokalnylekarz.projekt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lokalnylekarz.projekt.model.SearchDetails;
import pl.lokalnylekarz.projekt.model.User;

import java.util.Optional;

public interface SearchStatisticsRepository extends JpaRepository<SearchDetails, Long> {
    Optional<SearchDetails> findBySearchTypeAndValue(String searchType, String value);
    Optional<SearchDetails> findBySearchType(String searchType);

}
