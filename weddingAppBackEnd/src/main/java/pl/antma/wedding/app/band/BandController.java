package pl.antma.wedding.app.band;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bands")
public class BandController {

    @Autowired
    BandService bandService;

    @GetMapping
    public ResponseEntity<List<Band>> findAll() {
        return ResponseEntity.ok(bandService.findAll().collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<Band> addBand(@RequestBody Band band) {
        return ResponseEntity.ok(bandService.addBand(band));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Band> findBandById(@PathVariable Long id){
        return bandService
                .findBandById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Band> updateBand(@RequestBody Band band, @PathVariable Long id) {
        return bandService
                .updateBand(band, id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Band> deleteBand(@PathVariable Long id) {
        return bandService
                .deleteBand(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
