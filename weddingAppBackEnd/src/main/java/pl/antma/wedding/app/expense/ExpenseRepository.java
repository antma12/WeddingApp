package pl.antma.wedding.app.expense;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends PagingAndSortingRepository<Expense, Long> {
}
