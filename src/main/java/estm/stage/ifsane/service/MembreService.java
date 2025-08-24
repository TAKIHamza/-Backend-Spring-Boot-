package estm.stage.ifsane.service;
import org.springframework.stereotype.Service;

import estm.stage.ifsane.model.Membre;
import estm.stage.ifsane.repository.MembreRepository;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class MembreService {

    private final MembreRepository membreRepository;


    public MembreService(MembreRepository membreRepository) {
        this.membreRepository = membreRepository;
    }

    public List<Membre> getAllMembres() {
        return membreRepository.findAll();
    }
    public List<Membre> getMembresWithUtilisateur() {
        return membreRepository.findAllMembresWithUtilisateur();
    }
    public Optional<Membre> getMembreById(Long id) {
        return membreRepository.findById(id);
    }
    public Membre getMembreUtilisateurById(Long id) {
        return membreRepository.findByUtilisateurId(id);
    }

    public Membre createMembre(Membre membre) {
        return membreRepository.save(membre);
    }
    
    public Optional<Membre> updateMembre(Long id, Membre updatedMembre) {
        Optional<Membre> existingMembreOptional = membreRepository.findById(id);
        if (existingMembreOptional.isPresent()) {
            Membre existingMembre = existingMembreOptional.get();
            existingMembre.setPoste(updatedMembre.getPoste());
            existingMembre.setZone(updatedMembre.getZone());
            return Optional.of(membreRepository.save(existingMembre));
        } else {
            return Optional.empty();
        }
    }

    public void deleteMembre(Long id) {
        Membre membre = membreRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Membre non trouvé"));
    if (membre.getUtilisateur() != null) {
        membre.getUtilisateur().setMembre(null); // Déconnecte l'utilisateur du membre
    }
    membreRepository.delete(membre);
    }
}
