package pl.antma.wedding.app.guest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/guests")
public class GuestController {

    @Autowired
    GuestService guestService;

    @GetMapping
    public ResponseEntity<List<Guest>> findAll() {
        return ResponseEntity.ok(guestService.findAll().collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<Guest> addGuest(@RequestBody Guest guest) {
        return ResponseEntity.ok(guestService.addGuest(guest));
    }

    @GetMapping("/{username}")
    public ResponseEntity<Guest> findGuestByUsername(@PathVariable String username) {
        return guestService.findGuestByUsername(username).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{username}")
    public ResponseEntity<Guest> updateGuest(@RequestBody Guest guest, @PathVariable String username) {
        return guestService.updateGuest(guest, username).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Guest> deleteGuest(@PathVariable String username) {
        return guestService.deleteGuest(username).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
