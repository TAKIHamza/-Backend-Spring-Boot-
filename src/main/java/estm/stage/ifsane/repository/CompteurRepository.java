package estm.stage.ifsane.repository;

import estm.stage.ifsane.model.Compteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompteurRepository extends JpaRepository<Compteur, Long> {
    List<Compteur> findByPersonneId(Long personneId);
    List<Compteur> findByZoneName(String zoneName);
    
    @Query("SELECT COUNT(c) FROM Compteur c")
    long countAllCompteurs();

    // @Query("SELECT COUNT(c) FROM Compteur c WHERE c.zoneName = :zoneName")
    // long countCompteursByZoneName(String zoneName);
}
