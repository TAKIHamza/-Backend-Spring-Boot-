package estm.stage.ifsane.repository;

import estm.stage.ifsane.model.Consommation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConsommationRepository extends JpaRepository<Consommation, Long> {
    List<Consommation> findByCompteurId(Long compteurId);
    List<Consommation> findByCompteurZoneName(String zoneName);
    boolean existsByCompteurIdAndTrimestreAndAnnee(Long compteurId, int trimestre, int annee);
    
    @Query("SELECT c.annee, c.trimestre, SUM(c.quantite) FROM Consommation c GROUP BY c.annee, c.trimestre")
    List<Object[]> findSumQuantiteByAnneeAndTrimestre();

    @Query("SELECT c.annee, c.trimestre, SUM(c.quantite) FROM Consommation c WHERE c.compteur.zone.name = :zoneName GROUP BY c.annee, c.trimestre")
    List<Object[]> findSumQuantiteByAnneeAndTrimestreAndZone(String zoneName);

    @Query("SELECT c.annee, c.trimestre, SUM(c.quantite) FROM Consommation c WHERE c.compteur.personne.id = :personneId GROUP BY c.annee, c.trimestre")
    List<Object[]> findSumQuantiteByAnneeAndTrimestreAndPersonne(Long personneId);

    @Query("SELECT c.annee, c.trimestre, SUM(c.quantite) FROM Consommation c WHERE c.compteur.id = :compteurId GROUP BY c.annee, c.trimestre")
    List<Object[]> findSumQuantiteByAnneeAndTrimestreAndCompteur(Long compteurId);

    @Query("SELECT CONCAT(c.annee, '/', c.trimestre) AS anneeTrimestre, COUNT(c) AS nombreConsommations " +
       "FROM Consommation c " +
       "WHERE c.compteur.zone.name = :zoneName " +
       "GROUP BY c.annee, c.trimestre")
       List<Object[]> countConsommationsByAnneeTrimestreAndZone(@Param("zoneName") String zoneName);

}
