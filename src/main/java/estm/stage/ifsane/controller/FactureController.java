package estm.stage.ifsane.controller;

import estm.stage.ifsane.model.Facture;
import estm.stage.ifsane.service.FactureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/factures")
public class FactureController {

    @Autowired
    private FactureService factureService;

    @GetMapping
    public List<Facture> getAllFactures() {
        return factureService.getAllFactures();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Facture> getFactureById(@PathVariable Long id) {
        Optional<Facture> facture = factureService.getFactureById(id);
        return facture.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Facture createFacture(@RequestBody Facture facture) {
        return factureService.createFacture(facture);
    }
    @PutMapping("/pay/{id}")
    public ResponseEntity<Facture> payFacture(@PathVariable Long id) {
        try {
            Facture paidFacture = factureService.payFacture(id);
            return ResponseEntity.ok(paidFacture);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/paid")
    public List<Facture> getPaidFacturesOrderedByDate() {
        return factureService.getPaidFacturesOrderedByDate();
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Facture> updateFacture(@PathVariable Long id, @RequestBody Facture factureDetails) {
        try {
            Facture updatedFacture = factureService.updateFacture(id, factureDetails);
            return ResponseEntity.ok(updatedFacture);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFacture(@PathVariable Long id) {
        factureService.deleteFacture(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/compteur/{compteurId}")
    public List<Facture> getFacturesByCompteurId(@PathVariable Long compteurId) {
        return factureService.getFacturesByCompteurId(compteurId);
    }

    @GetMapping("/zone/{zoneName}")
    public List<Facture> getFacturesByZoneName(@PathVariable String zoneName) {
        return factureService.getFacturesByZoneName(zoneName);
    }

    @GetMapping("/trimestre/{trimestre}/annee/{annee}/zone/{zoneName}")
    public List<Facture> getFacturesByTrimestreAndAnneeAndZone(
        @PathVariable int trimestre,
        @PathVariable int annee,
        @PathVariable String zoneName
    ) {
        return factureService.getFacturesByTrimestreAndAnneeAndZone(trimestre, annee, zoneName);
    }
}
