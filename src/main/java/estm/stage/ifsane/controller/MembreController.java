package estm.stage.ifsane.controller;

import estm.stage.ifsane.model.Membre;
import estm.stage.ifsane.service.MembreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/membres")
public class MembreController {

    private final MembreService membreService;

    public MembreController(MembreService membreService) {
        this.membreService = membreService;
    }

    @GetMapping
    public ResponseEntity<List<Membre>> getAllMembres() {
        List<Membre> membres = membreService.getAllMembres();
        return ResponseEntity.ok(membres);
    }
    @GetMapping("/utilisateurs")
    public ResponseEntity<List<Membre>> getMembresWithUtilisateur() {
        List<Membre> membres = membreService.getMembresWithUtilisateur();
        return ResponseEntity.ok(membres);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Membre> getMembreById(@PathVariable Long id) {
        Optional<Membre> membre = membreService.getMembreById(id);
        return membre.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Membre> createMembre(@RequestBody Membre membre) {
        Membre createdMembre = membreService.createMembre(membre);
        return new ResponseEntity<>(createdMembre, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Membre> updateMembre(@PathVariable Long id, @RequestBody Membre updatedMembre) {
        Optional<Membre> updatedMembreOptional = membreService.updateMembre(id, updatedMembre);
        return updatedMembreOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }    

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMembre(@PathVariable Long id) {
        System.out.println(id);
        membreService.deleteMembre(id);
        return ResponseEntity.noContent().build();
    }
}
