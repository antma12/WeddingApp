package pl.antma.wedding.app.todo;

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
class TodoServiceTest {

    @Mock
    TodoRepository todoRepository;

    @InjectMocks
    TodoService todoService;

    public static List<Todo> todoList = Stream.generate(Todo::new).limit(10).collect(Collectors.toList());
    public static Page<Todo> todoPage = new PageImpl<>(todoList);
    public static Todo todo1;

    @BeforeAll
    public static void beforeAll() {
        todo1 = new Todo();
        todo1.setDescription("Ballroom");
    }

    @Test
    @DisplayName("findAll from service should return the same stream as repository")
    void testFindAll() {
        when(todoRepository.findAll(any(PageRequest.class))).thenReturn(todoPage);
        Stream<Todo> result = todoService.findAll();
        Assertions.assertEquals(todoList, result.collect(Collectors.toList()));
    }

    @Test
    @DisplayName("findTodoById with correct id should return optional of todo")
    void testFindTodoByIdExistentId() {
        when(todoRepository.findById(eq(1L))).thenReturn(Optional.of(todo1));
        Optional<Todo> result = todoService.findTodoById(1L);
        Assertions.assertEquals(Optional.of(todo1), result);
    }

    @Test
    @DisplayName("findTodoById with non-existent id should return empty optional")
    void testFindTodoByIdNonExistentId() {
        when(todoRepository.findById(eq(2L))).thenReturn(Optional.empty());
        Optional<Todo> result = todoService.findTodoById(2L);
        Assertions.assertEquals(Optional.empty(), result);
    }

    @Test
    @DisplayName("addTodo should return created todo")
    void testAddTodoNew() {
        when(todoRepository.save(todo1)).then(i -> i.getArgument(0, Todo.class));
        Todo result = todoService.addTodo(todo1);
        Assertions.assertEquals(todo1, result);
    }

    @Test
    @DisplayName("updateTodo should return optional of updated todo")
    void testUpdateTodoExisting() {
        when(todoRepository.findById(1L)).thenReturn(Optional.of(todo1));
        when(todoRepository.save(todo1)).then(i -> i.getArgument(0, Todo.class));
        Optional<Todo> result = todoService.updateTodo(todo1, 1L);
        Assertions.assertEquals(Optional.of(todo1), result);
    }

    @Test
    @DisplayName("updateTodo should return empty optional if todo with this id is not in db")
    void testUpdateTodoNonExisting() {
        when(todoRepository.findById(2L)).thenReturn(Optional.empty());
        Optional<Todo> result = todoService.updateTodo(null, 2L);
        Assertions.assertEquals(Optional.empty(), result);
    }

    @Test
    @DisplayName("deleteTodo should return optional of deleted todo")
    void testDeleteTodoExisting() {
        when(todoRepository.findById(1L)).thenReturn(Optional.of(todo1));
        Optional<Todo> result = todoService.deleteTodo(1L);
        Assertions.assertEquals(Optional.of(todo1), result);
    }

    @Test
    @DisplayName("deleteTodo should return empty optional if todo with this id is not in db")
    void testDeleteTodoNonExisting() {
        when(todoRepository.findById(2L)).thenReturn(Optional.empty());
        Optional<Todo> result = todoService.deleteTodo(2L);
        Assertions.assertEquals(Optional.empty(), result);
    }

}
