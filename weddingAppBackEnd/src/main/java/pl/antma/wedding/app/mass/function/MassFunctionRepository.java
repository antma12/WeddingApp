package pl.antma.wedding.app.mass.function;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MassFunctionRepository extends PagingAndSortingRepository<MassFunction, Long> {
}
