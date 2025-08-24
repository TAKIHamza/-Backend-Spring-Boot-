package estm.stage.ifsane.service;
import org.springframework.stereotype.Service;

import estm.stage.ifsane.model.Zone;
import estm.stage.ifsane.repository.ZoneRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ZoneService {

    private final ZoneRepository zoneRepository;


    public ZoneService(ZoneRepository zoneRepository) {
        this.zoneRepository = zoneRepository;
    }

    public List<Zone> getAllZones() {
        return zoneRepository.findAll();
    }

    public Optional<Zone> getZoneByName(String name) {
        return zoneRepository.findById(name);
    }
    
    public Zone updateZone(String name, Zone updatedZone) {
        return zoneRepository.findById(name)
            .map(zone -> {
                zone.setDescription(updatedZone.getDescription());
                return zoneRepository.save(zone);
            })
            .orElseThrow(() -> new IllegalArgumentException("Zone not found"));
    }

    public Zone createZone(Zone zone) {
        return zoneRepository.save(zone);
    }

    public void deleteZone(String name) {
        zoneRepository.deleteById(name);
    }
}
