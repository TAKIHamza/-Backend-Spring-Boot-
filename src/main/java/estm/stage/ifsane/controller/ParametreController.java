package estm.stage.ifsane.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import estm.stage.ifsane.model.Parametre;
import estm.stage.ifsane.service.ParametreService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/parametres")
public class ParametreController {

    private final ParametreService parametreService;

 
    public ParametreController(ParametreService parametreService) {
        this.parametreService = parametreService;
    }

    @GetMapping
    public List<Parametre> getAllParametres() {
        return parametreService.getAllParametres();
    }

    @GetMapping("/{name}")
    public ResponseEntity<Parametre> getParametreByName(@PathVariable String name) {
        Optional<Parametre> parametre = parametreService.getParametreByName(name);
        return parametre.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Parametre createParametre(@RequestBody Parametre parametre) {
        return parametreService.createParametre(parametre);
    }

    @PutMapping("/{name}")
    public ResponseEntity<Parametre> updateParametre(@PathVariable String name, @RequestBody Parametre parametre) {
        Parametre updatedParametre = parametreService.updateParametre(name, parametre);
        return updatedParametre != null ? ResponseEntity.ok(updatedParametre) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteParametre(@PathVariable String name) {
        parametreService.deleteParametre(name);
        return ResponseEntity.noContent().build();
    }
}
