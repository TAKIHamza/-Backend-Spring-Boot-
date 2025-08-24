package estm.stage.ifsane.controller;

import estm.stage.ifsane.model.Personne;
import estm.stage.ifsane.service.PersonneService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/personnes")
public class PersonneController {

    private final PersonneService personneService;

    public PersonneController(PersonneService personneService) {
        this.personneService = personneService;
    }

    @GetMapping
    public ResponseEntity<List<Personne>> getAllPersonnes() {
        List<Personne> personnes = personneService.getAllPersonnes();
        return ResponseEntity.ok(personnes);
    }

    @GetMapping("/members")
    public ResponseEntity<List<Personne>> getAllMembres() {
        List<Personne> personnes = personneService.getMembres();
        if (personnes.isEmpty()) {
         
            return ResponseEntity.noContent().build();
        } else {

            return ResponseEntity.ok(personnes);
        }
        
    }


    @GetMapping("/{id}")
    public ResponseEntity<Personne> getPersonneById(@PathVariable Long id) {
        Optional<Personne> personne = personneService.getPersonneById(id);
        return personne.map(ResponseEntity::ok)
                       .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Personne> createPersonne(@RequestBody Personne personne) {
      
        Personne existingPersonne = personneService.getPersonneByCNI(personne.getCni());
        if (existingPersonne != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        Personne createdPersonne = personneService.createPersonne(personne);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPersonne);
    }
@PutMapping("/{id}")
    public ResponseEntity<Personne> updatePersonne(@PathVariable Long id, @RequestBody Personne updatedPersonne) {
        try {
            Personne updated = personneService.updatePersonne(id, updatedPersonne);
            return ResponseEntity.ok(updated);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersonne(@PathVariable Long id) {
        personneService.deletePersonne(id);
        return ResponseEntity.noContent().build();
    }
}
