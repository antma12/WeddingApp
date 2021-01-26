package pl.antma.wedding.app.expense;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigInteger;
import java.util.Optional;

@DataJpaTest
class ExpenseRepositoryTest {

    @Autowired
    ExpenseRepository expenseRepository;

    @Test
    @DisplayName("findAll should return optional of deleted ballroom")
    void testFindAll() {
        Iterable<Expense> ballrooms = expenseRepository.findAll();
        Assertions.assertNotNull(ballrooms);
    }

    @Test
    @DisplayName("findById should return optional of ballroom if it is present")
    void testFindByIdPresent(){
        Optional<Expense> foundExpense = expenseRepository.findById(1L);
        Assertions.assertTrue(foundExpense.isPresent());
    }

    @Test
    @DisplayName("findById should return empty optional if id is present")
    void testFindByIdNotPresent(){
        Optional<Expense> foundExpense = expenseRepository.findById(2L);
        Assertions.assertFalse(foundExpense.isPresent());
    }

    @Test
    @DisplayName("save should persist ballroom in database and return ballroom object")
    void testSave() {
        Expense testExpense = new Expense();
        testExpense.setName("test");
        testExpense.setType("ballroom");
        testExpense.setMinCost(new BigInteger("4000"));
        testExpense.setMaxCost(new BigInteger("6000"));
        testExpense.setActualCost(new BigInteger("5000"));
        testExpense.setSplitCost(true);
        Expense returnedExpense = expenseRepository.save(testExpense);
        Assertions.assertEquals(testExpense, returnedExpense);
        Assertions.assertTrue(expenseRepository.findById(1L).isPresent());
    }
}
