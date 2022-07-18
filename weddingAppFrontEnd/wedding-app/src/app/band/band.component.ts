import { Component, Input } from "@angular/core";
import { Router } from "@angular/router";
import { BandService } from "./band.service";

@Component({
    selector: "band-component",
    templateUrl: "./band.component.html"
})
export class BandComponent {
    @Input() image : string = "/assets/band.jpg";
    public bands;
    public addMode = false;

    constructor(private bandService : BandService, private router : Router) {

    }

    ngOnInit() {
        this.getBands();
    }
    
    changeAddMode = function() {
        this.addMode = !this.addMode; 
    }

    submitForm = function(formValues) {
        this.bandService.addBand(formValues).subscribe( () => {
            this.getBands();
            this.changeAddMode();
            this.router.navigate(['/band']);
        }); 
    }

    getBands = function() {
        this.bandService.getBands().subscribe((bands) => this.bands = bands);
    }

    removeBand = function(band) {
        this.bandService.removeBand(band).subscribe(() => {this.getBands()});
    }
}