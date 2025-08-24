package estm.stage.ifsane.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import estm.stage.ifsane.model.Membre;
import estm.stage.ifsane.model.Utilisateur;
import estm.stage.ifsane.repository.MembreRepository;
import estm.stage.ifsane.repository.UtilisateurRepository;

import java.util.List;
import jakarta.persistence.EntityNotFoundException;

@Service
public class UtilisateurService {
 private final MembreRepository membreRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    
    public UtilisateurService(UtilisateurRepository utilisateurRepository, PasswordEncoder passwordEncoder ,MembreRepository m) {
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
        this.membreRepository = m;

    }

    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    public Utilisateur getUtilisateurById(Long id) {
        return utilisateurRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé avec l'ID : " + id));
    }

    @Transactional
    public Utilisateur createUtilisateur(Utilisateur utilisateur) {
        utilisateur.setPassword(passwordEncoder.encode(utilisateur.getPassword()));
        return utilisateurRepository.save(utilisateur);
    }

    
@Transactional
public void deleteUtilisateur(Long membreId) {
    Membre membre = membreRepository.findById(membreId)
                                    .orElseThrow(() -> new EntityNotFoundException("Membre non trouvé"));
    Utilisateur utilisateur = membre.getUtilisateur();
    if (utilisateur != null) {
        membre.setUtilisateur(null); // Rompre le lien avec le membre
        membreRepository.save(membre); // Enregistrer les modifications du membre
        
        // Supprimer l'utilisateur de la base de données
        utilisateurRepository.delete(utilisateur);
    }
}

@Transactional
    public void updatePassword(Long id, String oldPassword, String newPassword) {
        Utilisateur utilisateur = getUtilisateurById(id);
        if (!passwordEncoder.matches(oldPassword, utilisateur.getPassword())) {
            throw new IllegalArgumentException("Ancien mot de passe incorrect");
        }
        utilisateur.setPassword(passwordEncoder.encode(newPassword));
        utilisateurRepository.save(utilisateur);
    }

    @Transactional
    public Utilisateur updateUtilisateur(Long id, Utilisateur utilisateurDetails) {
        Utilisateur utilisateur = getUtilisateurById(id);
        utilisateur.setRole(utilisateurDetails.getRole());
        return utilisateurRepository.save(utilisateur);
    }

    // public Utilisateur login(String email, String password) {
    //     return utilisateurRepository.findByEmailAndPassword(email, password)
    //             .orElseThrow(() -> new RuntimeException("Identifiants invalides"));
    // }
}
