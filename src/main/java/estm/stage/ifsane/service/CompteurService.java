package estm.stage.ifsane.service;

import estm.stage.ifsane.model.Compteur;
import estm.stage.ifsane.repository.CompteurRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompteurService {

    private final CompteurRepository compteurRepository;

    public CompteurService(CompteurRepository compteurRepository) {
        this.compteurRepository = compteurRepository;
    }

    public List<Compteur> getAllCompteurs() {
        return compteurRepository.findAll();
    }

    public Long getCountAllCompteurs() {
        return compteurRepository.countAllCompteurs();
    }

    public Optional<Compteur> getCompteurById(Long id) {
        return compteurRepository.findById(id);
    }

    public List<Compteur> getCompteursByPersonneId(Long personneId) {
        return compteurRepository.findByPersonneId(personneId);
    }

    public List<Compteur> getCompteursByZoneName(String zoneName) {
        return compteurRepository.findByZoneName(zoneName);
    }

    public List<Compteur> insertListOfCompteurs(List<Compteur> compteurs) {
        return compteurRepository.saveAll(compteurs);
    }

    public Compteur updateCompteur(Long id, Compteur compteurDetails) {
        Optional<Compteur> compteurOptional = compteurRepository.findById(id);
        if (compteurOptional.isPresent()) {
            Compteur compteur = compteurOptional.get();
            compteur.setNumeroSerie(compteurDetails.getNumeroSerie());
            compteur.setDateInstallation(compteurDetails.getDateInstallation());
            compteur.setDernierReleve(compteurDetails.getDernierReleve());
            compteur.setPersonne(compteurDetails.getPersonne());
            compteur.setZone(compteurDetails.getZone());
            
            return compteurRepository.save(compteur);
        } else {
            return null;
        }
    }
    public void updateDernierReleve(Long compteurId, String dernierReleve) {
        compteurRepository.findById(compteurId).ifPresent(compteur -> {
            compteur.setDernierReleve(dernierReleve);
            compteurRepository.save(compteur);
        });
    }
    public void deleteCompteur(Long id) {
        compteurRepository.deleteById(id);
    }
}
