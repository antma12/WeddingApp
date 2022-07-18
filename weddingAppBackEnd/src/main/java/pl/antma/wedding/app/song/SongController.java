package pl.antma.wedding.app.song;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/songs")
public class SongController {

    @Autowired
    SongService songService;

    @GetMapping
    public ResponseEntity<List<Song>> findAll() {
        return ResponseEntity.ok(songService.findAll().collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<Song> addSong(@RequestBody Song song) {
        return ResponseEntity.ok(songService.addSong(song));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Song> findSongById(@PathVariable Long id) {
        return songService.findSongById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Song> updateSong(@RequestBody Song song, @PathVariable Long id) {
        return songService.updateSong(song, id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Song> deleteSong(@PathVariable Long id) {
        return songService.deleteSong(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
