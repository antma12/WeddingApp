package pl.antma.wedding.app.expense;

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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExpenseServiceTest {

    @Mock
    ExpenseRepository expenseRepository;

    @InjectMocks
    ExpenseService expenseService;

    public static List<Expense> expenseList =  Stream.generate(Expense::new).limit(10).collect(Collectors.toList());
    public static Page<Expense> expensePage = new PageImpl<>(expenseList);
    public static Expense expense1;

    @BeforeAll
    public static void beforeAll(){
        expense1 = new Expense();
        expense1.setName("Quadrans");
    }

    @Test
    @DisplayName("findAll from service should return the same stream as repository")
    void testFindAll() {
        when(expenseRepository.findAll(any(PageRequest.class))).thenReturn(expensePage);
        Stream<Expense> result = expenseService.findAll();
        Assertions.assertEquals(expenseList, result.collect(Collectors.toList()));
    }

    @Test
    @DisplayName("findExpenseById with correct id should return optional of expense")
    void testFindExpenseByIdExistentId() {
        when(expenseRepository.findById(eq(1L))).thenReturn(Optional.of(expense1));
        Optional<Expense> result = expenseService.findExpenseById(1L);
        Assertions.assertEquals(Optional.of(expense1), result);
    }

    @Test
    @DisplayName("findExpenseById with non-existent id should return empty optional")
    void testFindExpenseByIdNonExistentId() {
        when(expenseRepository.findById(eq(2L))).thenReturn(Optional.empty());
        Optional<Expense> result = expenseService.findExpenseById(2L);
        Assertions.assertEquals(Optional.empty(), result);
    }

    @Test
    @DisplayName("addExpense should return created expense")
    void testAddExpenseNew() {
        when(expenseRepository.save(expense1)).then(i -> i.getArgument(0, Expense.class));
        Expense result = expenseService.addExpense(expense1);
        Assertions.assertEquals(expense1, result);
    }

    @Test
    @DisplayName("updateExpense should return optional of updated expense")
    void testUpdateExpenseExisting() {
        when(expenseRepository.findById(1L)).thenReturn(Optional.of(expense1));
        when(expenseRepository.save(expense1)).then(i -> i.getArgument(0, Expense.class));
        Optional<Expense> result = expenseService.updateExpense(expense1, 1L);
        Assertions.assertEquals(Optional.of(expense1), result);
    }

    @Test
    @DisplayName("updateExpense should return empty optional if expense with this id is not in db")
    void testUpdateExpenseNonExisting() {
        when(expenseRepository.findById(2L)).thenReturn(Optional.empty());
        Optional<Expense> result = expenseService.updateExpense(null, 2L);
        Assertions.assertEquals(Optional.empty(), result);
    }

    @Test
    @DisplayName("deleteExpense should return optional of deleted expense")
    void testDeleteExpenseExisting() {
        when(expenseRepository.findById(1L)).thenReturn(Optional.of(expense1));
        Optional<Expense> result = expenseService.deleteExpense(1L);
        Assertions.assertEquals(Optional.of(expense1), result);
    }

    @Test
    @DisplayName("deleteExpense should return empty optional if expense with this id is not in db")
    void testDeleteExpenseNonExisting() {
        when(expenseRepository.findById(2L)).thenReturn(Optional.empty());
        Optional<Expense> result = expenseService.deleteExpense(2L);
        Assertions.assertEquals(Optional.empty(), result);
    }

}
