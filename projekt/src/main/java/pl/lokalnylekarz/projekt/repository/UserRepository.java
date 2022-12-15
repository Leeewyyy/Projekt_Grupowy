package pl.lokalnylekarz.projekt.repository;

import org.springframework.data.repository.CrudRepository;
import pl.lokalnylekarz.projekt.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
}
