package pl.antma.wedding.app.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    TodoService todoService;

    @GetMapping
    public ResponseEntity<List<Todo>> findAll() {
        return ResponseEntity.ok(todoService.findAll().collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<Todo> addTodo(@RequestBody Todo todo) {
        return ResponseEntity.ok(todoService.addTodo(todo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Todo> findTodoById(@PathVariable Long id) {
        return todoService.findTodoById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@RequestBody Todo todo, @PathVariable Long id) {
        return todoService.updateTodo(todo, id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Todo> deleteTodo(@PathVariable Long id) {
        return todoService.deleteTodo(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
