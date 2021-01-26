package pl.antma.wedding.app.guest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
class GuestRepositoryTest {

    private final String usernameInDB = "jkowalski";
    private final String usernameNotInDB = "mnowak";

    @Autowired
    GuestRepository guestRepository;

    @Test
    @DisplayName("findAll should return optional of deleted ballroom")
    void testFindAll() {
        Iterable<Guest> ballrooms = guestRepository.findAll();
        Assertions.assertNotNull(ballrooms);
    }

    @Test
    @DisplayName("findByUsername should return optional of ballroom if it is present")
    void testFindByIdPresent() {
        Optional<Guest> foundGuest = guestRepository.findByUsername(usernameInDB);
        Assertions.assertTrue(foundGuest.isPresent());
    }

    @Test
    @DisplayName("findByUsername should return empty optional if id is present")
    void testFindByIdNotPresent() {
        Optional<Guest> foundGuest = guestRepository.findById(usernameNotInDB);
        Assertions.assertFalse(foundGuest.isPresent());
    }
    
    @Test
    @DisplayName("save should persist ballroom in database and return ballroom object")
    void testSave() {
        Guest testGuest = new Guest();
        testGuest.setFirstName("name");
        testGuest.setSurname("surname");
        testGuest.setUsername("nsurname");
        testGuest.setSide("wife");
        Guest returnedGuest = guestRepository.save(testGuest);
        Assertions.assertEquals(testGuest, returnedGuest);
        Assertions.assertTrue(guestRepository.findByUsername("nsurname").isPresent());
    }
}
