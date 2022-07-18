import { Component, Input } from "@angular/core";
import { Router } from "@angular/router";
import { Expense } from "./Expense";
import { ExpenseService } from "./expense.service";

@Component({
    selector: "expenses-component",
    templateUrl: "./expenses.component.html"
})
export class ExpensesComponent {
    @Input() image : string = "/assets/expenses.jpg";
    public expenses : Expense[];
    public addMode : boolean = false;

    constructor(private expenseService : ExpenseService, private router : Router) {}

    ngOnInit() {
        this.getExpenses();
    }
    
    changeAddMode = function() {
        this.addMode = !this.addMode; 
    }

    submitForm = function(formValues) {
        this.expenseService.addExpense(formValues).subscribe( () => {
            this.getExpenses();
            this.changeAddMode();
            this.router.navigate(['/expenses']);
        }); 
    }

    getExpenses = function() {
        this.expenseService.getExpenses().subscribe((expenses) => this.expenses = expenses);
    }

    removeExpense = function(expense : Expense) {
        this.expenseService.removeExpense(expense).subscribe(() => {this.getExpenses()});
    }
}