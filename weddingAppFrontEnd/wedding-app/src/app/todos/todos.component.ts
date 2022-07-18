import { Component, Input } from "@angular/core";
import { Router } from "@angular/router";
import { TodoService } from "./TodoService";

@Component({
    selector: "todos-component",
    templateUrl: "./todos.component.html"
})
export class TodosComponent {
    @Input() image : string = "/assets/todos.jpg";

    public todos;
    public addMode = false;

    constructor(private todoService : TodoService, private router : Router){}

    ngOnInit() {
        this.getTodos();
    }

    changeAddMode = function() {
        this.addMode = !this.addMode; 
    }

    submitForm = function(formValues) {
        this.todoService.addTodo(formValues).subscribe( () => {
            this.getTodos();
            this.changeAddMode();
            this.router.navigate(['/todo']);
        }); 
    }
    
    getTodos() {
        this.todoService.getTodos()
            .subscribe(todos => this.todos = todos);
    }


    removeTodo = function(todo) {
        this.todoService.removeTodo(todo).subscribe(() => {this.getTodos()});
    }
}