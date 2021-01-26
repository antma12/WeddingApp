package pl.antma.wedding.app.expense;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Stream;

@Component
public class ExpenseService {

    @Autowired
    ExpenseRepository expenseRepository;

    Stream<Expense> findAll() {
        return expenseRepository.findAll(PageRequest.of(0, 20)).get();
    }

    public Expense addExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    public Optional<Expense> findExpenseById(Long id) {
        return expenseRepository.findById(id);
    }

    public Optional<Expense> updateExpense(Expense expense, Long id) {
        Optional<Expense> updatedExpense = expenseRepository.findById(id);
        updatedExpense.ifPresent(
                updExpense -> {
                    updExpense.setType(expense.getType());
                    updExpense.setName(expense.getName());
                    updExpense.setMinCost(expense.getMinCost());
                    updExpense.setMaxCost(expense.getMaxCost());
                    updExpense.setActualCost(expense.getActualCost());
                    updExpense.setSplitCost(expense.isSplitCost());
                    expenseRepository.save(updExpense);
                });
        return updatedExpense;
    }

    public Optional<Expense> deleteExpense(Long id) {
        Optional<Expense> deletedExpense = expenseRepository.findById(id);
        deletedExpense.ifPresent(expenseRepository::delete);
        return deletedExpense;
    }
}
