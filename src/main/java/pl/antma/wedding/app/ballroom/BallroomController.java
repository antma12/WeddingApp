package pl.antma.wedding.app.ballroom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ballrooms")
public class BallroomController {
    
    @Autowired
    public BallroomService ballroomService;

    @GetMapping
    public ResponseEntity<List<Ballroom>> findAll() {
        return ResponseEntity.ok(ballroomService.findAll().collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<Ballroom> addBallroom(@RequestBody Ballroom ballroom) {
        return ResponseEntity.ok(ballroomService.addBallroom(ballroom));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ballroom> findBallroomById(@PathVariable Long id){
        return ballroomService
                .findBallroomById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ballroom> updateBallroom(@RequestBody Ballroom ballroom, @PathVariable Long id) {
        return ballroomService
                .updateBallroom(ballroom, id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Ballroom> deleteBallroom(@PathVariable Long id) {
        return ballroomService
                .deleteBallroom(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
