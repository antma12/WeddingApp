import { Component, Input } from "@angular/core";
import { Router } from "@angular/router";
import { combineLatest, forkJoin, merge, observable, Observable } from "rxjs";
import { combineAll, concatAll, map } from "rxjs/operators";
import { Guest } from "../guests/Guest";
import { GuestService } from "../guests/guest.service";
import { MassFunctionService } from "./mass-function.service";
import { MassFunction } from "./MassFunction";

@Component({
    selector: "mass-functions-component",
    templateUrl: "./mass-functions.component.html"
})
export class MassFunctionsComponent {
    @Input() image : string = "/assets/massfunctions.jpg";
    public massFunctions;
    public guests : Guest[];
    public guestUsernames : string[];
    public addMode = false;
    public guestCollection = "";
    public guestNames = [];

    constructor(private massFunctionService : MassFunctionService, 
        private guestService : GuestService,
        private router : Router){
            
        }

    ngOnInit() {
        this.getMassFunctions();
    }

    changeAddMode = function() {
        this.addMode = !this.addMode; 
        this.guestCollection = "";
        this.guestNames = [];
    }

    submitForm = function(formValues) {
        var guestsInputValues = formValues.guests.split("###");
        console.log("guestsInputValues:" + guestsInputValues);

        let guestsX : [Observable<Guest>] = guestsInputValues.map
            (guest => this.guestService.getGuest(guest)
        );
        let guestArrayX : Observable<[Guest]> = forkJoin(guestsX);
        let massFunctionX : Observable<MassFunction> = guestArrayX.pipe(
            map(guests => {
                console.log(JSON.stringify(guests));
                let massFunction : MassFunction = {
                    "id" : null, 
                    "name" : formValues.name, 
                    "guests" : guests
                };
                return massFunction;
            })
        );
        let addMassFunctionRequestX : Observable<MassFunction> = massFunctionX.pipe(
            map(mf => this.massFunctionService.addMassFunction(mf)),
            concatAll()
        );
        addMassFunctionRequestX.subscribe(
            () => {
                this.guestCollection = "";
                this.guestNames = [];
                this.getMassFunctions();
                this.changeAddMode();
                this.router.navigate(['/massfunctions']);
            }
        );
    }
    
    getMassFunctions() {
        this.massFunctionService.getMassFunctions()
            .subscribe(massFunctions => this.massFunctions = massFunctions);
    }


    removeMassFunction = function(massFunction) {
        this.massFunctionService.removeMassFunction(massFunction).subscribe(() => {this.getMassFunctions()});
    }

    getGuestIds() {
        //TODO: create map: label -> value
        this.guestService.getGuests().subscribe(response => {
            this.guests = response;
            this.guestUsernames = response.map(guest => guest.username);
        });
    }

    forceSelection(element) {
        if(this.guestUsernames.indexOf(element.value) !== -1) {
            console.log(this.guestCollection);
            if(this.guestCollection === '') {
                this.guestCollection =  this.guestCollection + element.value;
                this.guestService.getGuest(element.value).subscribe(
                    g => this.guestNames.push(g.firstName + " " + g.surname)
                );
            }
            if(this.guestCollection.indexOf(element.value) === -1) {
                this.guestCollection =  this.guestCollection + "###" + element.value;
                this.guestService.getGuest(element.value).subscribe(
                    g => this.guestNames.push(g.firstName + " " + g.surname)
                );
            }
            element.value = '';
        }
    }

    removeGuest(guestName : string) {
        this.guestNames.splice(this.guestNames.indexOf(guestName), 1);
        this.guestCollection = this.guestCollection.split('###')
            .filter(el => el !== (guestName.substr(0, 1) + guestName.split(' ')[1]).toLowerCase())
            .join('###');
    }
}