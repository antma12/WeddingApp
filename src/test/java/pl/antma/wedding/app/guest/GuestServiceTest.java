package pl.antma.wedding.app.guest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GuestServiceTest {

    private static final String usernameInDB = "jkowalski";
    private static final String usernameNotInDB = "mnowak";

    @Mock
    GuestRepository guestRepository;

    @InjectMocks
    GuestService guestService;

    public static List<Guest> guestList =  Stream.generate(Guest::new).limit(10).collect(Collectors.toList());
    public static Page<Guest> guestPage = new PageImpl<>(guestList);
    public static Guest guest1;

    @BeforeAll
    public static void beforeAll(){
        guest1 = new Guest();
        guest1.setUsername(usernameInDB);
        guest1.setFirstName("janusz");
        guest1.setSurname("kowalski");
    }

    @Test
    @DisplayName("findAll from service should return the same stream as repository")
    void testFindAll() {
        when(guestRepository.findAll(any(PageRequest.class))).thenReturn(guestPage);
        Stream<Guest> result = guestService.findAll();
        Assertions.assertEquals(guestList, result.collect(Collectors.toList()));
    }

    @Test
    @DisplayName("findGuestById with correct id should return optional of guest")
    void testFindGuestByIdExistentId() {
        when(guestRepository.findByUsername(usernameInDB)).thenReturn(Optional.of(guest1));
        Optional<Guest> result = guestService.findGuestByUsername(usernameInDB);
        Assertions.assertEquals(Optional.of(guest1), result);
    }

    @Test
    @DisplayName("findByUsername with non-existent id should return empty optional")
    void testFindGuestByUsernameNonExistentId() {
        when(guestRepository.findByUsername(usernameNotInDB)).thenReturn(Optional.empty());
        Optional<Guest> result = guestService.findGuestByUsername(usernameNotInDB);
        Assertions.assertEquals(Optional.empty(), result);
    }

    @Test
    @DisplayName("addGuest should return created guest")
    void testAddGuestNew() {
        when(guestRepository.save(guest1)).then(i -> i.getArgument(0, Guest.class));
        Guest result = guestService.addGuest(guest1);
        Assertions.assertEquals(guest1, result);
    }

    @Test
    @DisplayName("updateGuest should return optional of updated guest")
    void testUpdateGuestExisting() {
        when(guestRepository.findByUsername(usernameInDB)).thenReturn(Optional.of(guest1));
        when(guestRepository.save(guest1)).then(i -> i.getArgument(0, Guest.class));
        Optional<Guest> result = guestService.updateGuest(guest1, usernameInDB);
        Assertions.assertEquals(Optional.of(guest1), result);
    }

    @Test
    @DisplayName("updateGuest should return empty optional if guest with this id is not in db")
    void testUpdateGuestNonExisting() {
        when(guestRepository.findByUsername(usernameNotInDB)).thenReturn(Optional.empty());
        Optional<Guest> result = guestService.updateGuest(null, usernameNotInDB);
        Assertions.assertEquals(Optional.empty(), result);
    }

    @Test
    @DisplayName("deleteGuest should return optional of deleted guest")
    void testDeleteGuestExisting() {
        when(guestRepository.findByUsername(usernameInDB)).thenReturn(Optional.of(guest1));
        Optional<Guest> result = guestService.deleteGuest(usernameInDB);
        Assertions.assertEquals(Optional.of(guest1), result);
    }

    @Test
    @DisplayName("deleteGuest should return empty optional if guest with this id is not in db")
    void testDeleteGuestNonExisting() {
        when(guestRepository.findByUsername(usernameNotInDB)).thenReturn(Optional.empty());
        Optional<Guest> result = guestService.deleteGuest(usernameNotInDB);
        Assertions.assertEquals(Optional.empty(), result);
    }

}
