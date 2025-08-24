package estm.stage.ifsane.repository;

import estm.stage.ifsane.model.Personne;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface PersonneRepository extends JpaRepository<Personne, Long> {
    Personne findByCni(String cni);
    List<Personne> findByType(int type);
    List<Personne> findByTypeIn(List<Integer> types);

    @Query("SELECT COUNT(p) FROM Personne p WHERE p.type = :type")
    long countByType(int type);
}
