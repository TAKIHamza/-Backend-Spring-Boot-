package estm.stage.ifsane.service;

import org.springframework.stereotype.Service;

import estm.stage.ifsane.model.Facture;
import estm.stage.ifsane.repository.FactureRepository;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class FactureService {

    private final FactureRepository factureRepository;

    public FactureService(FactureRepository factureRepository) {
        this.factureRepository = factureRepository;
    }

    public List<Facture> getAllFactures() {
        return factureRepository.findAll();
    }

    public Optional<Facture> getFactureById(Long id) {
        return factureRepository.findById(id);
    }

    public Facture createFacture(Facture facture) {
        return factureRepository.save(facture);
    }

    public Facture updateFacture(Long id, Facture factureDetails) {
        Optional<Facture> existfacture = factureRepository.findById(id);
           Facture   facture = existfacture.get();
        facture.setMontant(factureDetails.getMontant());
        facture.setDateEmission(factureDetails.getDateEmission());
        facture.setDateLimitePaiement(factureDetails.getDateLimitePaiement());
        facture.setDatePaiement(factureDetails.getDatePaiement());
        facture.setEstPayee(factureDetails.getEstPayee());
        facture.setConsommation(factureDetails.getConsommation());

        return factureRepository.save(facture);
    }
 public Facture payFacture(Long id) {
        Optional<Facture> existFacture = factureRepository.findById(id);
        Facture facture = existFacture.orElseThrow(() -> new NoSuchElementException("Facture not found"));
        facture.setEstPayee(1);  
        facture.setDatePaiement(new Date());  
        return factureRepository.save(facture);
    }
    
    public void deleteFacture(Long id) {
        factureRepository.deleteById(id);
    }

    public List<Facture> getFacturesByCompteurId(Long compteurId) {
        return factureRepository.findByConsommationCompteurId(compteurId);
    }

    public List<Facture> getFacturesByZoneName(String zoneName) {
        return factureRepository.findByConsommationCompteurZoneName(zoneName);
    }
    
    public List<Facture> getPaidFacturesOrderedByDate() {
        return factureRepository.findByEstPayeeOrderByDatePaiementDesc(1);
    }

    public List<Facture> getFacturesByTrimestreAndAnneeAndZone(int trimestre, int annee, String zoneName) {
        
        List<Facture> factures =  factureRepository.findByConsommationTrimestreAndAnneeAndZone(trimestre, annee, zoneName);
       
        // Fetch consumption history for the previous trimesters and years
        for (Facture facture : factures) {
            List<Object[]> consommationHistorique = factureRepository.findConsommationHistorique(
                facture.getConsommation().getCompteur().getId(),
                trimestre,
                annee
            );
            facture.getConsommation().setHistorique(consommationHistorique);
        }
        return factures;
    }
}
