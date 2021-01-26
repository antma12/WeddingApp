package pl.antma.wedding.app.guest;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GuestRepository extends PagingAndSortingRepository<Guest, String> {

    Optional<Guest> findByUsername(String username);
}
