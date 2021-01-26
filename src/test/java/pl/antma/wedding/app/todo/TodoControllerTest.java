package pl.antma.wedding.app.todo;

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
import pl.antma.wedding.app.song.Song;

import java.util.Optional;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TodoController.class)
class TodoControllerTest {

    @MockBean
    TodoService todoService;

    @Autowired
    private MockMvc mockMvc;

    Todo todo1;

    @BeforeEach
    public void before() {

        Stream<Todo> todoStream = Stream.generate(Todo::new).limit(10);
        todo1 = new Todo();
        todo1.setDescription("Ballroom");

        when(todoService.findAll()).thenReturn(todoStream);
        when(todoService.addTodo(any(Todo.class))).then(i -> i.getArgument(0, Todo.class));
        when(todoService.findTodoById(eq(1L))).thenReturn(Optional.of(todo1));
        when(todoService.findTodoById(eq(2L))).thenReturn(Optional.empty());
        when(todoService.deleteTodo(eq(1L))).thenReturn(Optional.of(todo1));
        when(todoService.deleteTodo(eq(2L))).thenReturn(Optional.empty());
        when(todoService.updateTodo(any(Todo.class), eq(1L))).then(i -> Optional.of(i.getArgument(0, Todo.class)));
        when(todoService.updateTodo(any(Todo.class), eq(2L))).thenReturn(Optional.empty());
    }

    @Test
    @DisplayName("GET /todos should return the same number of results as service")
    void testGetTodos() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(10)));
    }

    @Test
    @DisplayName("GET /todos/1 should return one todo")
    void testGetSpecificTodo() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/todos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Ballroom"));
    }

    @Test
    @DisplayName("POST /todos should return created todo")
    void testPostTodo() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/todos")
                .content(asJsonString(todo1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Ballroom"));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
