package pl.antma.wedding.app.viedographer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/videographers")
public class VideographerController {

    @Autowired
    VideographerService videographerService;

    @GetMapping
    public ResponseEntity<List<Videographer>> findAll() {
        return ResponseEntity.ok(videographerService.findAll().collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<Videographer> addVideographer(@RequestBody Videographer videographer) {
        return ResponseEntity.ok(videographerService.addVideographer(videographer));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Videographer> findVideographerById(@PathVariable Long id) {
        return videographerService.findVideographerById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Videographer> updateVideographer(@RequestBody Videographer videographer, @PathVariable Long id) {
        return videographerService.updateVideographer(videographer, id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Videographer> deleteVideographer(@PathVariable Long id) {
        return videographerService.deleteVideographer(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
