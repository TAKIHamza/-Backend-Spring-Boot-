package estm.stage.ifsane.service;
import estm.stage.ifsane.model.Personne;
import estm.stage.ifsane.repository.PersonneRepository; 

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PersonneService { 

    private final PersonneRepository personneRepository; 


    public PersonneService(PersonneRepository personneRepository) {
    
        this.personneRepository = personneRepository;
    }

    public List<Personne> getAllPersonnes() { 
        return personneRepository.findByTypeIn(Arrays.asList(0, 2));
    }

    public Optional<Personne> getPersonneById(Long id) { 
        return personneRepository.findById(id); 
    }
    
    public List<Personne> getMembres() { 
        return personneRepository.findByTypeIn(Arrays.asList(1, 2));
    }

    public Personne getPersonneByCNI(String cni) {
        return personneRepository.findByCni(cni);
    }

    public Personne createPersonne(Personne personne) { 
        return personneRepository.save(personne);
    }

       public Personne updatePersonne(Long id ,Personne updatedPersonne) {
        Optional<Personne> existingPersonneOptional = personneRepository.findById(id);

        if (existingPersonneOptional.isPresent()) {
            Personne existingPersonne = existingPersonneOptional.get();
            existingPersonne.setNom(updatedPersonne.getNom());
            existingPersonne.setPrenom(updatedPersonne.getPrenom());
            existingPersonne.setCni(updatedPersonne.getCni());
            existingPersonne.setAdresse(updatedPersonne.getAdresse());
            existingPersonne.setTelephone(updatedPersonne.getTelephone());
            existingPersonne.setType(updatedPersonne.getType());
            return personneRepository.save(existingPersonne);
        } else {
            throw new NoSuchElementException("Person not found with ID: " + updatedPersonne.getId());
        }
    }
    public void deletePersonne(Long id) { 
        personneRepository.deleteById(id);
    }
}
