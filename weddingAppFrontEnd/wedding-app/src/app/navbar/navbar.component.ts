import { Component } from '@angular/core';

@Component({
  selector: 'navbar',
  templateUrl: './navbar.component.html',
  styles: ['li > a.active { font-weight: bold; }'],
})
export class NavbarComponent {}
