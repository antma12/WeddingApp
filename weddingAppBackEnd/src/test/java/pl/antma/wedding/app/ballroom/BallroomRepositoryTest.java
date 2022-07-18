package pl.antma.wedding.app.ballroom;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
class BallroomRepositoryTest {

    @Autowired
    BallroomRepository ballroomRepository;

    @Test
    @DisplayName("findAll should return optional of deleted ballroom")
    void testFindAll() {
        Iterable<Ballroom> ballrooms = ballroomRepository.findAll();
        Assertions.assertNotNull(ballrooms);
    }

    @Test
    @DisplayName("findById should return optional of ballroom if it is present")
    void testFindByIdPresent(){
        Optional<Ballroom> foundBallroom = ballroomRepository.findById(1L);
        Assertions.assertTrue(foundBallroom.isPresent());
    }

    @Test
    @DisplayName("findById should return empty optional if id is present")
    void testFindByIdNotPresent(){
        Optional<Ballroom> foundBallroom = ballroomRepository.findById(2L);
        Assertions.assertFalse(foundBallroom.isPresent());
    }

    @Test
    @DisplayName("save should persist ballroom in database and return ballroom object")
    void testSave() {
        Ballroom testBallroom = new Ballroom();
        testBallroom.setName("test");
        testBallroom.setCity("Poznan");
        testBallroom.setDistance(120D);
        Ballroom returnedBallroom = ballroomRepository.save(testBallroom);
        Assertions.assertEquals(testBallroom, returnedBallroom);
        Assertions.assertTrue(ballroomRepository.findById(1L).isPresent());
    }
}
