package pl.antma.wedding.app.videographer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.antma.wedding.app.viedographer.Videographer;
import pl.antma.wedding.app.viedographer.VideographerRepository;

import java.util.Optional;

@DataJpaTest
class VideographerRepositoryTest {

    @Autowired
    VideographerRepository videographerRepository;

    @Test
    @DisplayName("findAll should return optional of found videographers")
    void testFindAll() {
        Iterable<Videographer> videographers = videographerRepository.findAll();
        Assertions.assertNotNull(videographers);
    }

    @Test
    @DisplayName("findById should return optional of videographer if it is present")
    void testFindByIdPresent() {
        Optional<Videographer> foundVideographer = videographerRepository.findById(1L);
        Assertions.assertTrue(foundVideographer.isPresent());
    }

    @Test
    @DisplayName("findById should return empty optional if id is not present")
    void testFindByIdNotPresent() {
        Optional<Videographer> foundVideographer = videographerRepository.findById(2L);
        Assertions.assertFalse(foundVideographer.isPresent());
    }

    @Test
    @DisplayName("save should persist videographer in database and return videographer object")
    void testSave() {
        Videographer testVideographer = new Videographer();
        testVideographer.setName("Spoko Studio");
        testVideographer.setCameraman(true);
        testVideographer.setChosen(true);
        testVideographer.setPhotographer(true);

        Videographer returnedVideographer = videographerRepository.save(testVideographer);
        Assertions.assertEquals(testVideographer, returnedVideographer);
        Assertions.assertTrue(videographerRepository.findById(1L).isPresent());
    }

}
