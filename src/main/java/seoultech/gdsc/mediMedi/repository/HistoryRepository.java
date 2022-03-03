package seoultech.gdsc.mediMedi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seoultech.gdsc.mediMedi.entity.History;
import seoultech.gdsc.mediMedi.entity.Medi;
import seoultech.gdsc.mediMedi.entity.User;

import java.util.List;
import java.util.Optional;

public interface HistoryRepository extends JpaRepository<History, Integer> {
    Optional<History> getByUserAndMedi(User user, Medi medi);
}
