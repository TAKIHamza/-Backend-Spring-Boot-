package estm.stage.ifsane.repository;

import estm.stage.ifsane.model.Facture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FactureRepository extends JpaRepository<Facture, Long> {
    List<Facture> findByConsommationCompteurId(Long compteurId);
    List<Facture> findByConsommationCompteurZoneName(String zoneName);

    @Query("SELECT f FROM Facture f WHERE f.consommation.trimestre = :trimestre AND f.consommation.annee = :annee " +
    "AND f.consommation.compteur.zone.name = :zoneName")
List<Facture> findByConsommationTrimestreAndAnneeAndZone(int trimestre, int annee, String zoneName);

    List<Facture> findByEstPayeeOrderByDatePaiementDesc(Integer estPayee);
    
    @Query("SELECT f.consommation.trimestre, f.consommation.annee, SUM(f.consommation.quantite) FROM Facture f " +
           "WHERE f.consommation.compteur.id = :compteurId AND " +
           "(f.consommation.trimestre < :trimestre OR f.consommation.annee < :annee) " +
           "GROUP BY f.consommation.trimestre, f.consommation.annee " +
           "ORDER BY f.consommation.annee DESC, f.consommation.trimestre DESC")
    List<Object[]> findConsommationHistorique(Long compteurId, int trimestre, int annee);


    @Query("SELECT COUNT(f) FROM Facture f WHERE f.estPayee = 0")
    long countNonPayeeFactures();

    @Query("SELECT COUNT(f) FROM Facture f WHERE f.estPayee = 1")
    long countPayeeFactures();

    @Query("SELECT f.consommation.trimestre, f.consommation.annee, COUNT(f) FROM Facture f WHERE f.estPayee = 0 GROUP BY f.consommation.trimestre, f.consommation.annee")
    List<Object[]> countNonPayeeFacturesByTrimestreAndAnneeGrouped();

    @Query("SELECT f.consommation.trimestre, f.consommation.annee, COUNT(f) FROM Facture f WHERE f.estPayee = 1 GROUP BY f.consommation.trimestre, f.consommation.annee")
    List<Object[]> countPayeeFacturesByTrimestreAndAnneeGrouped();

    @Query("SELECT SUM(f.montant) FROM Facture f WHERE f.estPayee = 0")
    Double sumNonPayeeFactures();

    @Query("SELECT SUM(f.montant) FROM Facture f WHERE f.estPayee = 1")
    Double sumPayeeFactures();

    @Query("SELECT f.consommation.trimestre, f.consommation.annee, SUM(f.montant) FROM Facture f  GROUP BY f.consommation.trimestre, f.consommation.annee")
    List<Object[]> sumFacturesByTrimestreAndAnneeGrouped();

    @Query("SELECT f.consommation.trimestre, f.consommation.annee, SUM(f.montant) FROM Facture f WHERE f.estPayee = 0 GROUP BY f.consommation.trimestre, f.consommation.annee")
    List<Object[]> sumNonPayeeFacturesByTrimestreAndAnneeGrouped();

    @Query("SELECT f.consommation.trimestre, f.consommation.annee, SUM(f.montant) FROM Facture f WHERE f.estPayee = 1 GROUP BY f.consommation.trimestre, f.consommation.annee")
    List<Object[]> sumPayeeFacturesByTrimestreAndAnneeGrouped();

    @Query("SELECT f.consommation.compteur.zone.name, COUNT(f) FROM Facture f WHERE f.estPayee = 0 GROUP BY f.consommation.compteur.zone.name")
    List<Object[]> countNonPayeeFacturesByZoneGrouped();

    @Query("SELECT f.consommation.compteur.zone.name, COUNT(f) FROM Facture f WHERE f.estPayee = 1 GROUP BY f.consommation.compteur.zone.name")
    List<Object[]> countPayeeFacturesByZoneGrouped();

    @Query("SELECT f.consommation.compteur.zone.name, SUM(f.montant) FROM Facture f WHERE f.estPayee = 0 GROUP BY f.consommation.compteur.zone.name")
    List<Object[]> sumNonPayeeFacturesByZoneGrouped();

    @Query("SELECT f.consommation.compteur.zone.name, SUM(f.montant) FROM Facture f WHERE f.estPayee = 1 GROUP BY f.consommation.compteur.zone.name")
    List<Object[]> sumPayeeFacturesByZoneGrouped();

    @Query("SELECT f.consommation.trimestre, f.consommation.annee, f.consommation.compteur.zone.name, COUNT(f) FROM Facture f WHERE f.estPayee = 0 GROUP BY f.consommation.trimestre, f.consommation.annee, f.consommation.compteur.zone.name")
    List<Object[]> countNonPayeeFacturesByTrimestreAnneeAndZoneGrouped();

    @Query("SELECT f.consommation.trimestre, f.consommation.annee, f.consommation.compteur.zone.name, COUNT(f) FROM Facture f WHERE f.estPayee = 1 GROUP BY f.consommation.trimestre, f.consommation.annee, f.consommation.compteur.zone.name")
    List<Object[]> countPayeeFacturesByTrimestreAnneeAndZoneGrouped();

    @Query("SELECT f.consommation.trimestre, f.consommation.annee, f.consommation.compteur.zone.name, SUM(f.montant) FROM Facture f WHERE f.estPayee = 0 GROUP BY f.consommation.trimestre, f.consommation.annee, f.consommation.compteur.zone.name")
    List<Object[]> sumNonPayeeFacturesByTrimestreAnneeAndZoneGrouped();

    @Query("SELECT f.consommation.trimestre, f.consommation.annee, f.consommation.compteur.zone.name, SUM(f.montant) FROM Facture f WHERE f.estPayee = 1 GROUP BY f.consommation.trimestre, f.consommation.annee, f.consommation.compteur.zone.name")
    List<Object[]> sumPayeeFacturesByTrimestreAnneeAndZoneGrouped();
}
