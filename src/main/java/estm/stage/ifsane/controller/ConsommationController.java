package estm.stage.ifsane.controller;

import estm.stage.ifsane.model.Consommation;
import estm.stage.ifsane.service.ConsommationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/consommations")
public class ConsommationController {

    @Autowired
    private ConsommationService consommationService;

    @GetMapping
    public List<Consommation> getAllConsommations() {
        return consommationService.getAllConsommations();
    }
    @GetMapping("/statistics")
    public Map<String, Object> getAllStatistics() {
        return consommationService.getAllStatistics();
    }

    @GetMapping("/statistics/{zoneName}")
    public ResponseEntity<List<Object[]>> getConsommationStatisticsByZone(@PathVariable String zoneName) {
        List<Object[]> statistics = consommationService.getConsommationStatisticsByZone(zoneName);
        return new ResponseEntity<>(statistics, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Consommation> getConsommationById(@PathVariable Long id) {
        Optional<Consommation> consommation = consommationService.getConsommationById(id);
        return consommation.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PostMapping("/batch")
    public ResponseEntity<List<Consommation>> createConsommations(@RequestBody List<Consommation> consommations) {
        List<Consommation> createdConsommations = consommationService.createConsommations(consommations);
        return ResponseEntity.ok(createdConsommations);
    }
    @PostMapping
    public Consommation createConsommation(@RequestBody Consommation consommation) {
        return consommationService.createConsommation(consommation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Consommation> updateConsommation(@PathVariable Long id, @RequestBody Consommation consommationDetails) {
        try {
            Consommation updatedConsommation = consommationService.updateConsommation(id, consommationDetails);
            return ResponseEntity.ok(updatedConsommation);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConsommation(@PathVariable Long id) {
        consommationService.deleteConsommation(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/compteur/{compteurId}")
    public List<Consommation> getConsommationsByCompteurId(@PathVariable Long compteurId) {
        return consommationService.getConsommationsByCompteurId(compteurId);
    }

    @GetMapping("/zone/{zoneName}")
    public List<Consommation> getConsommationsByZoneName(@PathVariable String zoneName) {
        return consommationService.getConsommationsByZoneName(zoneName);
    }
}
