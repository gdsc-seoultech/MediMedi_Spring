package seoultech.gdsc.mediMedi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import seoultech.gdsc.mediMedi.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByToken(@Param(value="token") String token);
}
