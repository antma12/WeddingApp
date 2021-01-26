package pl.antma.wedding.app.song;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
class SongRepositoryTest {

    @Autowired
    SongRepository songRepository;

    @Test
    @DisplayName("findAll should return optional of deleted song")
    void testFindAll() {
        Iterable<Song> songs = songRepository.findAll();
        Assertions.assertNotNull(songs);
    }

    @Test
    @DisplayName("findById should return optional of song if it is present")
    void testFindByIdPresent() {
        Optional<Song> foundSong = songRepository.findById(1L);
        Assertions.assertTrue(foundSong.isPresent());
    }

    @Test
    @DisplayName("findById should return empty optional if id is not present")
    void testFindByIdNotPresent() {
        Optional<Song> foundSong = songRepository.findById(2L);
        Assertions.assertFalse(foundSong.isPresent());
    }

    @Test
    @DisplayName("save should persist song in database and return song object")
    void testSave() {
        Song testSong = new Song();
        testSong.setName("test");
        testSong.setArtist("testArtist");
        testSong.setComment("really good");

        Song returnedSong = songRepository.save(testSong);
        Assertions.assertEquals(testSong, returnedSong);
        Assertions.assertTrue(songRepository.findById(1L).isPresent());
    }
}
