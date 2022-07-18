package pl.antma.wedding.app.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Stream;

@Component
public class TodoService {

    @Autowired
    TodoRepository todoRepository;

    public Stream<Todo> findAll() {
        return todoRepository.findAll(PageRequest.of(0, 20)).get();
    }

    public Todo addTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    public Optional<Todo> findTodoById(Long id) {
        return todoRepository.findById(id);
    }

    public Optional<Todo> updateTodo(Todo todo, Long id) {
        Optional<Todo> updatedTodo = todoRepository.findById(id);
        updatedTodo.ifPresent(
                updTodo -> {
                    updTodo.setDescription(todo.getDescription());
                    updTodo.setYear(todo.getYear());
                    updTodo.setMonth(todo.getMonth());
                    updTodo.setDone(todo.isDone());
                    todoRepository.save(updTodo);
                });
        return updatedTodo;
    }

    public Optional<Todo> deleteTodo(Long id) {
        Optional<Todo> deletedTodo = todoRepository.findById(id);
        deletedTodo.ifPresent(todoRepository::delete);
        return deletedTodo;
    }
}
