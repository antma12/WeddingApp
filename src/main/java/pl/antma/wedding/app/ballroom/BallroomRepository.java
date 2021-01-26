package pl.antma.wedding.app.ballroom;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BallroomRepository extends PagingAndSortingRepository<Ballroom, Long> {
}
