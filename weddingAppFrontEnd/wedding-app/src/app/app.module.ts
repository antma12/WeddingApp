import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { NavbarComponent } from './navbar/navbar.component';
import { ThumbnailComponent } from './thumbnail/thumbnail.component';
import { BallroomComponent } from './ballroom/ballroom.component';
import { RouterModule } from '@angular/router';
import { appRoutes } from './routes';
import { MainDescriptionComponent } from './main-description/main-description.component';
import { BandComponent } from './band/band.component';
import { SongsComponent } from './songs/songs.component';
import { GuestsComponent } from './guests/guests.component';
import { TodosComponent } from './todos/todos.component';
import { VideographerComponent } from './videographer/videographer.component';
import { MassFunctionsComponent } from './mass-functions/mass-functions.component';
import { ExpensesComponent } from './expenses/expenses.component';
import { BallroomService } from './ballroom/ballroom.service';
import { GuestService } from './guests/guest.service';
import { FormsModule } from '@angular/forms';
import { BandService } from './band/band.service';
import { VideographerService } from './videographer/videographer.service';
import { SongService } from './songs/songs.service';
import { MassFunctionService } from './mass-functions/mass-function.service';
import { TodoService } from './todos/TodoService';
import { ExpenseService } from './expenses/expense.service';
import { NumberInput } from './number-input/number-input.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    ThumbnailComponent,
    NumberInput,
    MainDescriptionComponent,
    BallroomComponent,
    BandComponent,
    SongsComponent,
    GuestsComponent,
    MassFunctionsComponent,
    ExpensesComponent,
    TodosComponent,
    VideographerComponent
  ],
  imports: [
    BrowserModule,
    RouterModule,
    RouterModule.forRoot(appRoutes),
    HttpClientModule,
    FormsModule,
  ],
  providers: [
    BallroomService,
    GuestService,
    BandService,
    VideographerService,
    SongService,
    MassFunctionService,
    TodoService,
    ExpenseService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
