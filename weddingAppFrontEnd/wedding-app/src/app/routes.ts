import { Routes } from "@angular/router";
import { BallroomComponent } from "./ballroom/ballroom.component";
import { BandComponent } from "./band/band.component";
import { ExpensesComponent } from "./expenses/expenses.component";
import { GuestsComponent } from "./guests/guests.component";
import { MassFunctionsComponent } from "./mass-functions/mass-functions.component";
import { SongsComponent } from "./songs/songs.component";
import { TodosComponent } from "./todos/todos.component";
import { VideographerComponent } from "./videographer/videographer.component";

export const appRoutes : Routes = [
    {path: "ballroom", component: BallroomComponent},
    {path: "guests", component: GuestsComponent},
    {path: "band", component: BandComponent},
    {path: "videographer", component: VideographerComponent},
    {path: "songs", component: SongsComponent},
    {path: "massfunctions", component: MassFunctionsComponent},
    {path: "todos", component: TodosComponent},
    {path: "expenses", component: ExpensesComponent}

]
