package pl.antma.wedding.app.expense;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ExpenseController.class)
class ExpenseControllerTest {

    @MockBean
    ExpenseService expenseService;

    @Autowired
    private MockMvc mockMvc;

    Expense expense1;

    @BeforeEach
    public void before(){

        Stream<Expense> expenseStream =  Stream.generate(Expense::new).limit(10);
        expense1 = new Expense();
        expense1.setName("Ballroom");

        when(expenseService.findAll()).thenReturn(expenseStream);
        when(expenseService.addExpense(any(Expense.class))).then(i -> i.getArgument(0, Expense.class));
        when(expenseService.findExpenseById(eq(1L))).thenReturn(Optional.of(expense1));
        when(expenseService.findExpenseById(eq(2L))).thenReturn(Optional.empty());
        when(expenseService.deleteExpense(eq(1L))).thenReturn(Optional.of(expense1));
        when(expenseService.deleteExpense(eq(2L))).thenReturn(Optional.empty());
        when(expenseService.updateExpense(any(Expense.class), eq(1L))).then(i -> Optional.of(i.getArgument(0, Expense.class)));
        when(expenseService.updateExpense(any(Expense.class), eq(2L))).thenReturn(Optional.empty());
    }

    @Test
    @DisplayName("GET /expenses should return the same number of results as service")
    void testGetExpense() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/expenses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(10)));
    }

    @Test
    @DisplayName("GET /expenses/1 should return one expense")
    void testGetSpecificExpense() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/expenses/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ballroom"));
    }

    @Test
    @DisplayName("POST /expenses should return created expense")
    void testPostExpense() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/expenses")
                .content(asJsonString(expense1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ballroom"));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
