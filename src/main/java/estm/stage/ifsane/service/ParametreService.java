package estm.stage.ifsane.service;

import org.springframework.stereotype.Service;
import estm.stage.ifsane.model.Parametre;
import estm.stage.ifsane.repository.ParametreRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ParametreService {

    private final ParametreRepository parametreRepository;

   
    public ParametreService(ParametreRepository parametreRepository) {
        this.parametreRepository = parametreRepository;
    }

    public List<Parametre> getAllParametres() {
        return parametreRepository.findAll();
    }

    public Optional<Parametre> getParametreByName(String name) {
        return parametreRepository.findById(name);
    }

    public Parametre createParametre(Parametre parametre) {
        return parametreRepository.save(parametre);
    }

    public Parametre updateParametre(String name, Parametre parametre) {
        Optional<Parametre> existingParametre = parametreRepository.findById(name);
        if (existingParametre.isPresent()) {
            Parametre updatedParametre = existingParametre.get();
            updatedParametre.setValue(parametre.getValue());
            return parametreRepository.save(updatedParametre);
        }
        return null;
    }

    public void deleteParametre(String name) {
        parametreRepository.deleteById(name);
    }
}
