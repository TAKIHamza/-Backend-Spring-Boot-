package estm.stage.ifsane.service;
import org.springframework.stereotype.Service;

import estm.stage.ifsane.model.Localisation;
import estm.stage.ifsane.repository.LocalisationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class LocalisationService {

    private final LocalisationRepository localisationRepository;
    public LocalisationService(LocalisationRepository localisationRepository) {
        this.localisationRepository = localisationRepository;
    }

    public List<Localisation> getAllLocalisations() {
        return localisationRepository.findAll();
    }

    public Optional<Localisation> getLocalisationById(Long id) {
        return localisationRepository.findById(id);
    }

    public Localisation createLocalisation(Localisation localisation) {
        return localisationRepository.save(localisation);
    }

    public void deleteLocalisation(Long id) {
        localisationRepository.deleteById(id);
    }
}
