package pl.antma.wedding.app.mass.function;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.antma.wedding.app.guest.Guest;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@DataJpaTest
class MassFunctionRepositoryTest {

    @Autowired
    MassFunctionRepository massFunctionRepository;

    @Test
    @DisplayName("findAll should return optional of deleted mass function")
    void testFindAll() {
        Iterable<MassFunction> massFunctions = massFunctionRepository.findAll();
        Assertions.assertNotNull(massFunctions);
    }

    @Test
    @DisplayName("findById should return optional of mass function if it is present")
    void testFindByIdPresent() {
        Optional<MassFunction> foundMassFunction = massFunctionRepository.findById(1L);
        Assertions.assertTrue(foundMassFunction.isPresent());
    }

    @Test
    @DisplayName("findById should return empty optional if id is not present")
    void testFindByIdNotPresent() {
        Optional<MassFunction> foundMassFunction = massFunctionRepository.findById(2L);
        Assertions.assertFalse(foundMassFunction.isPresent());
    }

    @Test
    @DisplayName("save should persist mass function in database and return mass function object")
    void testSave() {
        MassFunction testMassFunction = new MassFunction();
        testMassFunction.setName("test");

        Guest guest1 = new Guest();
        Guest guest2 = new Guest();
        guest1.setUsername("testGuest1");
        guest2.setUsername("testGuest2");

        Set<Guest> guests = Stream.of(guest1, guest2).collect(Collectors.toSet());
        testMassFunction.setGuests(guests);

        MassFunction returnedMassFunction = massFunctionRepository.save(testMassFunction);
        Assertions.assertEquals(testMassFunction, returnedMassFunction);
        Assertions.assertTrue(massFunctionRepository.findById(1L).isPresent());
    }
}
