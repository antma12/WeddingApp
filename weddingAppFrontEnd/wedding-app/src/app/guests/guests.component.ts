import { Component, Input } from '@angular/core';
import { GuestService } from './guest.service';

@Component({
  selector: 'guests-component',
  templateUrl: './guests.component.html',
})
export class GuestsComponent {
  @Input() image: string = '/assets/guests.jpg';
  public guests;
  public addMode = false;

  constructor(private guestService: GuestService) {}

  ngOnInit() {
    this.getGuests();
  }

  changeAddMode = function () {
    this.addMode = !this.addMode;
  };

  submitForm = function (formValues) {
    this.guestService.addGuest(formValues).subscribe(() => {
      this.getGuests();
      this.changeAddMode();
      this.router.navigate(['/guests']);
    });
  };

  getGuests = function () {
    this.guestService.getGuests().subscribe(guests => (this.guests = guests));
  };

  removeGuest = function (guest) {
    this.guestService.removeGuest(guest).subscribe(() => {
      this.getGuests();
    });
  };
}
