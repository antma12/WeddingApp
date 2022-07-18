import { ChangeDetectorRef, Component, Input, SimpleChanges } from "@angular/core";
import { NgModel } from "@angular/forms";
import { Router } from "@angular/router";
import { Ballroom } from "./Ballroom";
import { BallroomService } from "./ballroom.service";

@Component({
    selector: "ballroom-component",
    templateUrl: "./ballroom.component.html",
    styles: [`.input-sub-element {
        // background-color: greenyellow;
        background-color: #3e3e3e;
        color: white;
        display: inline-block;
        margin: 10px;
        margin-left: 0px;
        margin-top: 0px;
        padding: 10px;
        // border: grey;
        border-radius: 8px;
        // border-style: solid;
    }
    .button-x {
        padding-left: 8px; 
        color: gray; 
        cursor: pointer;
    }
    // .error input {border: solid #E05C65;}
    em {
        color:#E05C65; 
        padding-left:15px;
        display: none;    
    }
    .error-em {display: inline-block;}
    .error-in {border: solid #E05C65;}
    .error-in:focus {box-shadow: 0 0 0 0.2rem rgba(224, 92, 101, 0.25);}
    `]
})
export class BallroomComponent {
    @Input() image : string = "/assets/ballroom.jpg";
    public ballrooms;
    public addMode = false;
    public editMode = false;
    public editedBallroom;
    public availableDatesAdd = [];
    public availableDatesEdit = [];
    // public distanceAdd;

    formErrors : any = {};

    constructor(private ballroomService : BallroomService, 
        private router : Router,
        private changeDetector : ChangeDetectorRef) {

    }

    ngOnInit() {
        this.getBallrooms();
    }

    changeAddMode = function() {
        this.addMode = !this.addMode; 
        this.availableDatesAdd = [];
    }

    changeEditMode = function(ballroom) {
        if(ballroom) {
            this.editedBallroom = ballroom;
            this.availableDatesEdit = ballroom.availableDates;
        } else {
            this.editedBallroom = null;
            this.availableDatesEdit = [];
        }
        this.editMode = !this.editMode; 
    }

    submitAddForm = function(formValues) {
        console.log(formValues);
        
        let distance = this.handleNumberField(formValues.distance);
        let price = this.handleNumberField(formValues.price);
        let pricePerNight = this.handleNumberField(formValues.pricePerNight);
        
        let newBallroom : Ballroom = {
            id : null,
            name : formValues.name,
            city : formValues.city,
            distance : distance,
            availableDates : formValues.dates,
            price : price,
            pricePerNight : pricePerNight,
            isWithNightStay : formValues.isWithNightStay,
            opinion : formValues.opinion
        };
        this.ballroomService.addBallroom(newBallroom).subscribe( () => {
            this.getBallrooms();
            this.changeAddMode();
            this.router.navigate(['/ballroom']);
        }); 
    }

    private handleNumberField = function(fieldValue) {
        let result: number;
        if(fieldValue !== "" && fieldValue != null) {
            fieldValue = fieldValue.replaceAll(',', '.');
            if(fieldValue.indexOf('.') !== -1) {
                fieldValue = fieldValue.slice(0, fieldValue.lastIndexOf(".") + 3);
            }
            result = +fieldValue;
        }
        return result;
    }

    submitEditForm = function(formValues) {
        let editedBallroom : Ballroom = {
            id : formValues.id,
            name : formValues.name,
            city : formValues.city,
            distance : formValues.distance,
            availableDates : formValues.dates,
            price : formValues.price,
            pricePerNight : formValues.pricePerNight,
            isWithNightStay : formValues.isWithNightStay,
            opinion : formValues.opinion
        };
        this.ballroomService.updateBallroom(editedBallroom).subscribe( () => {
            this.getBallrooms();
            this.changeEditMode();
            this.router.navigate(['/ballroom']);
        }); 
    }

    getBallrooms() {
        this.ballroomService.getBallrooms().subscribe((ballrooms) => this.ballrooms = ballrooms);
    }

    removeBallroom = function(ballroom) {
        this.ballroomService.removeBallroom(ballroom).subscribe(() => {this.getBallrooms()});
    }

    handleDateInputAdd = function(datesInput) {
        this.handleDateInput(datesInput, this.availableDatesAdd);
    }

    handleDateInputEdit = function(datesInput) {
        this.handleDateInput(datesInput, this.availableDatesEdit);
    }

    handleDateInput = function(datesInput, datesList) {
        if(!datesInput || !datesInput.value) {
            return;
        }
        //handle only yyyy-mm-dd format because <input type="date"> ensures that
        let dateParts = datesInput.value.split("-");

        if(dateParts.length !== 3 || dateParts[0].length !== 4 || dateParts[1].length !== 2 || dateParts[2].length !== 2){
            return;
        }
        if(dateParts[0] < 1990 || dateParts[1] > 12 || dateParts[2] > 31) {
            return;
        }
        if(datesList.indexOf(datesInput.value) === -1 && datesInput.value !== '') {
            datesList.push(datesInput.value);
        }
        datesInput.value = '';
        datesInput.blur();
    }

    removeDateAdd = function(date) {
        this.availableDatesAdd.splice(this.availableDatesAdd.indexOf(date), 1);
    }

    removeDateEdit = function(date) {
        this.availableDatesEdit.splice(this.availableDatesEdit.indexOf(date), 1);
    }

    validateNumber = function(inputElement) {
        let value = inputElement.value;

        if(this.formErrors[inputElement.name] === undefined) {
            this.formErrors[inputElement.name] = {};
        }
        this.formErrors[inputElement.name].isLessThanZero = false;
        this.formErrors[inputElement.name].isNotANumber = false;

        value = value.replaceAll(',', '.');
        if(value.indexOf('.') !== -1) {
            value = value.slice(0, value.lastIndexOf(".") + 3);
        }
        
        if(isNaN(value)) {
            this.formErrors[inputElement.name].isNotANumber = true;
            console.log("==NaN==");
            return;
        }

        if(+value < 0) {
            this.formErrors[inputElement.name].isLessThanZero = true;
            console.log("==less than zero==");
            return;
        }

        if(value.indexOf('.') !== -1) {
            console.log("value.indexOf('.') !== -1");
            inputElement.value = value.slice(0, value.lastIndexOf(".") + 3);
            value = value.slice(0, value.lastIndexOf(".") + 3);
        }
        console.log("setting final value: " + value);
    }

    isFormInvalid = function() {
        if(this.formErrors == null || this.formErrors == undefined) {
            return false;
        }

        for(let inputName of Object.keys(this.formErrors)) {
            let inputErrorObject = this.formErrors[inputName];
            for(let key of Object.keys(inputErrorObject)) {
                if(inputErrorObject[key]) {
                    return true;
                }
            }
        }
    }
}