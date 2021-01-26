package pl.antma.wedding.app.todo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.util.Optional;

@DataJpaTest
class TodoRepositoryTest {

    @Autowired
    TodoRepository todoRepository;

    @Test
    @DisplayName("findAll should return optional of found todos")
    void testFindAll() {
        Iterable<Todo> todos = todoRepository.findAll();
        Assertions.assertNotNull(todos);
    }

    @Test
    @DisplayName("findById should return optional of todo if it is present")
    void testFindByIdPresent() {
        Optional<Todo> foundTodo = todoRepository.findById(1L);
        Assertions.assertTrue(foundTodo.isPresent());
    }

    @Test
    @DisplayName("findById should return empty optional if id is not present")
    void testFindByIdNotPresent() {
        Optional<Todo> foundTodo = todoRepository.findById(2L);
        Assertions.assertFalse(foundTodo.isPresent());
    }

    @Test
    @DisplayName("save should persist todo in database and return todo object")
    void testSave() {
        Todo testTodo = new Todo();
        testTodo.setDescription("test");
        testTodo.setDone(true);
        testTodo.setMonth("July");
        testTodo.setYear("2021");

        Todo returnedTodo = todoRepository.save(testTodo);
        Assertions.assertEquals(testTodo, returnedTodo);
        Assertions.assertTrue(todoRepository.findById(1L).isPresent());
    }


}
