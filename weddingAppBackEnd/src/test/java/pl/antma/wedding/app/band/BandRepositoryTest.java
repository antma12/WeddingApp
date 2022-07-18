package pl.antma.wedding.app.band;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigInteger;
import java.util.Optional;

@DataJpaTest
class BandRepositoryTest {

    @Autowired
    BandRepository bandRepository;

    @Test
    @DisplayName("findAll should return optional of deleted ballroom")
    void testFindAll() {
        Iterable<Band> ballrooms = bandRepository.findAll();
        Assertions.assertNotNull(ballrooms);
    }

    @Test
    @DisplayName("findById should return optional of ballroom if it is present")
    void testFindByIdPresent(){
        Optional<Band> foundBand = bandRepository.findById(1L);
        Assertions.assertTrue(foundBand.isPresent());
    }

    @Test
    @DisplayName("findById should return empty optional if id is present")
    void testFindByIdNotPresent(){
        Optional<Band> foundBand = bandRepository.findById(2L);
        Assertions.assertFalse(foundBand.isPresent());
    }

    @Test
    @DisplayName("save should persist ballroom in database and return ballroom object")
    void testSave() {
        Band testBand = new Band();
        testBand.setName("test");
        testBand.setInfo("kuj-pom");
        testBand.setOpinion("pretty good");
        testBand.setPrice(new BigInteger("4000"));
        Band returnedBand = bandRepository.save(testBand);
        Assertions.assertEquals(testBand, returnedBand);
        Assertions.assertTrue(bandRepository.findById(1L).isPresent());
    }
}
