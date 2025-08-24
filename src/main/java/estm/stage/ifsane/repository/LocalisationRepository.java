package estm.stage.ifsane.repository;

import estm.stage.ifsane.model.Localisation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalisationRepository extends JpaRepository<Localisation, Long> {
}