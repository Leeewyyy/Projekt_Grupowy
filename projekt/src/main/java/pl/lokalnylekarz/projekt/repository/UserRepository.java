package pl.lokalnylekarz.projekt.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pl.lokalnylekarz.projekt.model.MedicalFacility;
import pl.lokalnylekarz.projekt.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByEmailAndPassword(String email, String password);
    Optional<User> findByEmail(String email);
    @Query("select u.favoriteFacilities from User u where u.id=?1")
    List<MedicalFacility> findFavoriteFacilitiesForUser(Long userId);
    List<User> findByVerificationDateIsNotNull();
}
