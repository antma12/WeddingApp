package pl.antma.wedding.app.mass.function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/massfunctions")
public class MassFunctionController {

    @Autowired
    MassFunctionService massFunctionService;

    @GetMapping
    public ResponseEntity<List<MassFunction>> findAll() {
        return ResponseEntity.ok(massFunctionService.findAll().collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<MassFunction> addMassFunction(@RequestBody MassFunction massFunction) {
        return ResponseEntity.ok(massFunctionService.addMassFunction(massFunction));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MassFunction> findMassFunctionById(@PathVariable Long id) {
        return massFunctionService.findMassFunctionById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MassFunction> updateMassFunction(@RequestBody MassFunction massFunction, @PathVariable Long id) {
        return massFunctionService.updateMassFunction(massFunction, id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteMassFunction(@PathVariable Long id) {
        return massFunctionService.deleteMassFunction(id)
                .map(MassFunction::getId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
