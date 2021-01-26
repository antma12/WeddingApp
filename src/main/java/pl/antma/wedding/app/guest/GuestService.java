package pl.antma.wedding.app.guest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Stream;

@Component
public class GuestService {

    @Autowired
    GuestRepository guestRepository;

    public Stream<Guest> findAll() {
        return guestRepository.findAll(PageRequest.of(0, 20)).get();
    }

    public Guest addGuest(Guest guest) {
        //TODO: zabezpiecznia: może mieć już ustawiony username albo nie mieć imienia i nazwiska
        String username = (guest.getFirstName().substring(0, 1) + guest.getSurname()).toLowerCase();
        guest.setUsername(username);
        return guestRepository.save(guest);
    }

    public Optional<Guest> findGuestByUsername(String username) {
        return guestRepository.findByUsername(username);
    }

    public Optional<Guest> updateGuest(Guest guest, String username) {
        Optional<Guest> updatedGuest = guestRepository.findByUsername(username);
        updatedGuest.ifPresent(
                updGuest -> {
                    updGuest.setFirstName(guest.getFirstName());
                    updGuest.setSurname(guest.getSurname());
                    updGuest.setSide(guest.getSide());
                    guestRepository.save(updGuest);
                });
        return updatedGuest;
    }

    public Optional<Guest> deleteGuest(String username) {
        Optional<Guest> deletedGuest = guestRepository.findByUsername(username);
        deletedGuest.ifPresent(guestRepository::delete);
        return deletedGuest;
    }
}
