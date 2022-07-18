package pl.antma.wedding.app.viedographer;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideographerRepository extends PagingAndSortingRepository<Videographer, Long> {
}
