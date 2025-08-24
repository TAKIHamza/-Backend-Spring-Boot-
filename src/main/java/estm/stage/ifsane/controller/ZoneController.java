package estm.stage.ifsane.controller;

import estm.stage.ifsane.model.Zone;
import estm.stage.ifsane.service.ZoneService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/zones")
public class ZoneController {

    private final ZoneService zoneService;

    public ZoneController(ZoneService zoneService) {
        this.zoneService = zoneService;
    }

    @GetMapping
    public List<Zone> getAllZones() {
        return zoneService.getAllZones();
    }

    @GetMapping("/{name}")
    public ResponseEntity<Zone> getZoneByName(@PathVariable String name) {
        return zoneService.getZoneByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Zone createZone(@RequestBody Zone zone) {
        return zoneService.createZone(zone);
    }

    @PutMapping("/{name}")
    public ResponseEntity<Zone> updateZone(@PathVariable String name, @RequestBody Zone updatedZone) {
        try {
            Zone zone = zoneService.updateZone(name, updatedZone);
            return ResponseEntity.ok(zone);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteZone(@PathVariable String name) {
        zoneService.deleteZone(name);
        return ResponseEntity.noContent().build();
    }
}
