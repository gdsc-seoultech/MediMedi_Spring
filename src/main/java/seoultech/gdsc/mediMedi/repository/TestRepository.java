package seoultech.gdsc.mediMedi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import seoultech.gdsc.mediMedi.entity.Test;

@Repository
public interface TestRepository extends JpaRepository<Test, Integer> {
}
