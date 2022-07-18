import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { VideographerService } from './videographer.service';

@Component({
  selector: 'videographer-component',
  templateUrl: './videographer.component.html',
})
export class VideographerComponent {
  @Input() image: string = '/assets/videographer.jpg';
  public videographers;
  public newVideographer;
  public addMode = false;

  constructor(
    private videographerService: VideographerService,
    private router: Router
  ) {}

  ngOnInit() {
    this.getVideographers();
  }

  changeAddMode = function () {
    this.addMode = !this.addMode;
  };

  submitForm = function (formValues) {
    this.videographerService.addVideographer(formValues).subscribe(() => {
      this.getVideographers();
      this.changeAddMode();
      this.router.navigate(['/videographer']);
    });
  };

  getVideographers = function () {
    this.videographerService
      .getVideographers()
      .subscribe(videographers => (this.videographers = videographers));
  };

  removeVideographer = function (videographer) {
    this.videographerService.removeVideographer(videographer).subscribe(() => {
      this.getVideographers();
    });
  };
}
