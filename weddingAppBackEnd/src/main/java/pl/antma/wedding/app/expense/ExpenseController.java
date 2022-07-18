package pl.antma.wedding.app.expense;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    @Autowired
    ExpenseService expenseService;

    @GetMapping
    public ResponseEntity<List<Expense>> findAll() {
        return ResponseEntity.ok(expenseService.findAll().collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<Expense> addExpense(@RequestBody Expense expense) {
        return ResponseEntity.ok(expenseService.addExpense(expense));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Expense> findExpenseById(@PathVariable Long id) {
        return expenseService.findExpenseById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Expense> updateExpense(@RequestBody Expense expense, @PathVariable Long id) {
        return expenseService.updateExpense(expense, id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Expense> deleteExpense(@PathVariable Long id) {
        return expenseService.deleteExpense(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
