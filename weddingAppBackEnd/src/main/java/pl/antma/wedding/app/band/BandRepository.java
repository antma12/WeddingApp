package pl.antma.wedding.app.band;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BandRepository extends PagingAndSortingRepository<Band, Long> {
}
