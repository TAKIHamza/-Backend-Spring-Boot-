package estm.stage.ifsane.controller;

import estm.stage.ifsane.model.Compteur;
import estm.stage.ifsane.service.CompteurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/compteurs")
public class CompteurController {

    @Autowired
    private CompteurService compteurService;

    // Get all compteurs
    @GetMapping
    public List<Compteur> getAllCompteurs() {
        return compteurService.getAllCompteurs();
    }

    // Get compteur by ID
    @GetMapping("/{id}")
    public ResponseEntity<Compteur> getCompteurById(@PathVariable Long id) {
        Optional<Compteur> compteur = compteurService.getCompteurById(id);
        return compteur.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/count")
    public Long getCountAllCompteurs() {
        return compteurService.getCountAllCompteurs();
    }

    // Get compteurs by personne ID
    @GetMapping("/personne/{personneId}")
    public List<Compteur> getCompteursByPersonneId(@PathVariable Long personneId) {
        return compteurService.getCompteursByPersonneId(personneId);
    }

    // Get compteurs by zone name
    @GetMapping("/zone/{zoneName}")
    public List<Compteur> getCompteursByZoneName(@PathVariable String zoneName) {
        return compteurService.getCompteursByZoneName(zoneName);
    }

    // Insert list of compteurs
    @PostMapping("/list")
    public List<Compteur> insertListOfCompteurs(@RequestBody List<Compteur> compteurs) {
        return compteurService.insertListOfCompteurs(compteurs);
    }

    // Update compteur
    @PutMapping("/{id}")
    public ResponseEntity<Compteur> updateCompteur(@PathVariable Long id, @RequestBody Compteur compteurDetails) {
        Compteur updatedCompteur = compteurService.updateCompteur(id, compteurDetails);
        if (updatedCompteur != null) {
            return ResponseEntity.ok(updatedCompteur);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete compteur
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompteur(@PathVariable Long id) {
        compteurService.deleteCompteur(id);
        return ResponseEntity.noContent().build();
    }
}
