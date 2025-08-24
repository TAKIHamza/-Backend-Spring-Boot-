package estm.stage.ifsane.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import estm.stage.ifsane.model.Membre;

@Repository
public interface MembreRepository extends JpaRepository<Membre, Long> {
     @Query("SELECT m FROM Membre m WHERE m.utilisateur IS NOT NULL")
    List<Membre> findAllMembresWithUtilisateur();
    Membre findByUtilisateurId(long id);
    
}
