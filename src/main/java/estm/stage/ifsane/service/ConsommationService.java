package estm.stage.ifsane.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import estm.stage.ifsane.model.Compteur;
import estm.stage.ifsane.model.Consommation;
import estm.stage.ifsane.model.Facture;
import estm.stage.ifsane.repository.CompteurRepository;
import estm.stage.ifsane.repository.ConsommationRepository;
import estm.stage.ifsane.repository.FactureRepository;
import estm.stage.ifsane.repository.PersonneRepository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ConsommationService {

    @Autowired
    private ConsommationRepository consommationRepository;

    @Autowired
    private FactureRepository factureRepository;

    @Autowired
    private CompteurRepository compteurRepository;

    @Autowired
    private PersonneRepository personneRepository;

    @Autowired
    private ParametreService parametreService;
    

    public Map<String, Object> getAllStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("compteurs", compteurRepository.countAllCompteurs());
        stats.put("consommateurs", personneRepository.countByType(0)+personneRepository.countByType(2));
       
        stats.put("sumQuantiteByAnneeAndTrimestre", consommationRepository.findSumQuantiteByAnneeAndTrimestre());
       
        stats.put("countNonPayeeFactures", factureRepository.countNonPayeeFactures());
        stats.put("countPayeeFactures", factureRepository.countPayeeFactures());
        

        stats.put("countNonPayeeFacturesByTrimestreAndAnneeGrouped", factureRepository.countNonPayeeFacturesByTrimestreAndAnneeGrouped());
        stats.put("countPayeeFacturesByTrimestreAndAnneeGrouped", factureRepository.countPayeeFacturesByTrimestreAndAnneeGrouped());

        stats.put("sumNonPayeeFactures", factureRepository.sumNonPayeeFactures());
        stats.put("sumPayeeFactures", factureRepository.sumPayeeFactures());
        stats.put("sumFactures", factureRepository.sumFacturesByTrimestreAndAnneeGrouped());

        stats.put("sumNonPayeeFacturesByTrimestreAndAnneeGrouped", factureRepository.sumNonPayeeFacturesByTrimestreAndAnneeGrouped());
        stats.put("sumPayeeFacturesByTrimestreAndAnneeGrouped", factureRepository.sumPayeeFacturesByTrimestreAndAnneeGrouped());

        stats.put("countNonPayeeFacturesByZoneGrouped", factureRepository.countNonPayeeFacturesByZoneGrouped());
        stats.put("countPayeeFacturesByZoneGrouped", factureRepository.countPayeeFacturesByZoneGrouped());

        stats.put("sumNonPayeeFacturesByZoneGrouped", factureRepository.sumNonPayeeFacturesByZoneGrouped());
        stats.put("sumPayeeFacturesByZoneGrouped", factureRepository.sumPayeeFacturesByZoneGrouped());

        stats.put("countNonPayeeFacturesByTrimestreAnneeAndZoneGrouped", factureRepository.countNonPayeeFacturesByTrimestreAnneeAndZoneGrouped());
        stats.put("countPayeeFacturesByTrimestreAnneeAndZoneGrouped", factureRepository.countPayeeFacturesByTrimestreAnneeAndZoneGrouped());

        stats.put("sumNonPayeeFacturesByTrimestreAnneeAndZoneGrouped", factureRepository.sumNonPayeeFacturesByTrimestreAnneeAndZoneGrouped());
        stats.put("sumPayeeFacturesByTrimestreAnneeAndZoneGrouped", factureRepository.sumPayeeFacturesByTrimestreAnneeAndZoneGrouped());

        return stats;
    }

    public List<Consommation> createConsommations(List<Consommation> consommations) {
        List<Consommation> savedConsommations = new ArrayList<>();
    
        for (Consommation consommation : consommations) {
            Compteur compteur = consommation.getCompteur();
    
            // Vérifier si le compteur existe dans la base de données
            Optional<Compteur> optionalCompteur = compteurRepository.findById(compteur.getId());
            if (optionalCompteur.isPresent()) {
                compteur = optionalCompteur.get();
                consommation.setCompteur(compteur);
    
                if (!consommationRepository.existsByCompteurIdAndTrimestreAndAnnee(
                        compteur.getId(), consommation.getTrimestre(), consommation.getAnnee())) {
    
                    // Sauvegarder la consommation
                    consommation = consommationRepository.save(consommation);
                    savedConsommations.add(consommation);
    
                    double lastReading = Double.parseDouble(compteur.getDernierReleve());
                    double currentReading = consommation.getValeurCompteur().doubleValue();
    
                    double rate1 = Double.parseDouble(parametreService.getParametreByName("price1").get().getValue());
                    double threshold1 = Double.parseDouble(parametreService.getParametreByName("volume1").get().getValue());
                    double rate2 = Double.parseDouble(parametreService.getParametreByName("price2").get().getValue());
                    double threshold2 = Double.parseDouble(parametreService.getParametreByName("volume2").get().getValue());
                    double rate3 = Double.parseDouble(parametreService.getParametreByName("price3").get().getValue());
                    double maxReading = Double.parseDouble(parametreService.getParametreByName("volume3").get().getValue());
                    double fixedPrice = Double.parseDouble(parametreService.getParametreByName("fixed_tariff").get().getValue());
    
                    double cost = calculateCost(lastReading, currentReading, rate1, threshold1, rate2, threshold2, rate3, maxReading, fixedPrice);
    
                    Facture facture = new Facture();
                    facture.setConsommation(consommation);
                    facture.setMontant(cost);
                    facture.setDateEmission(new Date());
    
                    LocalDate dateEmission = LocalDate.now();
                    LocalDate dateLimitePaiement = dateEmission.plusDays(90);
                    facture.setDateLimitePaiement(Date.from(dateLimitePaiement.atStartOfDay(ZoneId.systemDefault()).toInstant()));
    
                    facture.setDatePaiement(facture.getDateEmission()); // à remplir lors du paiement
                    facture.setEstPayee(0); // initialement non payée
    
                    factureRepository.save(facture);
    
                    compteur.setDernierReleve(String.valueOf(currentReading));
                    compteurRepository.save(compteur);
                } else {
                    // La consommation existe déjà, gérer ce cas si nécessaire
                    System.out.println("Consommation already exists for compteur ID " + compteur.getId() +
                            " for trimestre " + consommation.getTrimestre() + " and year " + consommation.getAnnee());
                }
            } else {
                // Le compteur n'existe pas, gestion de l'erreur
                throw new RuntimeException("Compteur ID " + compteur.getId() + " does not exist.");
            }
        }
    
        return savedConsommations;
    }
    
    
    public List<Object[]> getConsommationStatisticsByZone(String zoneName) {
        List<Object[]> results = consommationRepository.countConsommationsByAnneeTrimestreAndZone(zoneName);
       
        return results;
    }
    

    private double calculateCost(double lastReading, double currentReading, double rate1, double threshold1, double rate2, double threshold2, double rate3, double maxReading, double fixedPrice) {
        double totalCost = 0.0;
        if (currentReading <= lastReading) {
            return totalCost;
        }
        double consumed = currentReading - lastReading;
        if (consumed <= threshold1) {
            totalCost = consumed * rate1;
        } else if (consumed <= threshold2) {
            totalCost = threshold1 * rate1 + (consumed - threshold1) * rate2;
        } else {
            totalCost = threshold1 * rate1 + (threshold2 - threshold1) * rate2 + (consumed - threshold2) * rate3;
        }
        return totalCost + fixedPrice;
    }

    public List<Consommation> getAllConsommations() {
        return consommationRepository.findAll();
    }

    public Optional<Consommation> getConsommationById(Long id) {
        return consommationRepository.findById(id);
    }

    public Consommation createConsommation(Consommation consommation) {
        return consommationRepository.save(consommation);
    }
    

    public Consommation updateConsommation(Long id, Consommation consommationDetails) {
        Optional<Consommation> existconsommation = consommationRepository.findById(id);
         Consommation consommation = existconsommation.get();       
        consommation.setTrimestre(consommationDetails.getTrimestre());
        consommation.setAnnee(consommationDetails.getAnnee());
        consommation.setQuantite(consommationDetails.getQuantite());
        consommation.setValeurCompteur(consommationDetails.getValeurCompteur());
        consommation.setCompteur(consommationDetails.getCompteur());

        return consommationRepository.save(consommation);
    }

    public void deleteConsommation(Long id) {
        consommationRepository.deleteById(id);
    }

    public List<Consommation> getConsommationsByCompteurId(Long compteurId) {
        return consommationRepository.findByCompteurId(compteurId);
    }

    public List<Consommation> getConsommationsByZoneName(String zoneName) {
        return consommationRepository.findByCompteurZoneName(zoneName);
    }
}
