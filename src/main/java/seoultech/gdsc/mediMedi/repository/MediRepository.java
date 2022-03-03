package seoultech.gdsc.mediMedi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import seoultech.gdsc.mediMedi.entity.Medi;

import java.util.HashSet;
import java.util.List;

public interface MediRepository extends JpaRepository<Medi, Integer> {
    HashSet<Medi> getAllByNameLike(String name);
    HashSet<Medi> getAllByEntpLike(String entp);
    HashSet<Medi> getAllByEffectLike(String effect);
}
