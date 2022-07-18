import { Component, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'thumbnail',
  templateUrl: './thumbnail.component.html',
})
export class ThumbnailComponent {
  @Input() src: string; // = "/assets/ballroom.jpg";

  // ngOnInit() {
  //     this.src = "/assets" +  + ".jpg";
  // }
}
