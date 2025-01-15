import { CommonModule } from "@angular/common";
import { HttpClientTestingModule } from "@angular/common/http/testing";
import { ComponentFixture, TestBed } from "@angular/core/testing";
import { FormsModule } from "@angular/forms";
import { BrowserModule, By } from "@angular/platform-browser";
import { RouterTestingModule } from "@angular/router/testing";
import { of } from "rxjs";
import { AppComponent } from "../app.component";
import { Ballroom } from "./Ballroom";
import { BallroomComponent } from "./ballroom.component";
import { BallroomService } from "./ballroom.service";

describe("GIVEN BallroomComponent", () => {
    let fixture;
    let component;
    let ballroomService;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            imports: [HttpClientTestingModule, RouterTestingModule, FormsModule/*, BrowserModule, CommonModule*/],
            providers : [
                BallroomComponent,
                {provide: BallroomService, useClass: BallroomService}
            ],
            declarations : [/*AppComponent,*/ BallroomComponent]
        }).compileComponents();
        fixture = TestBed.createComponent(BallroomComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
        ballroomService = TestBed.inject(BallroomService);
        //TODO: when detectChanges() is required?
        // fixture.detectChanges();
    });

    it("SHOULD getBallrooms on init", () => {
        const ballroom1 : Ballroom = {
            id : 1,
            name: "Riverside",
            city : "Wloclawek",
            distance : 10,
            availableDates : [new Date()],
            price : 230,
            pricePerNight: 100,
            isWithNightStay: true,
            opinion: "very good"
        };
        spyOn(ballroomService, "getBallrooms").and.returnValue(of([ballroom1]));
        component.ngOnInit();
        expect(component.ballrooms).toContain(ballroom1);
    });

    it("SHOULD render new ballroom form on changeAddMode()", () => {
        const ballroom1 : Ballroom = {
            id : 1,
            name: "Riverside",
            city : "Wloclawek",
            distance : 10,
            availableDates : [new Date()],
            price : 230,
            pricePerNight: 100,
            isWithNightStay: true,
            opinion: "very good"
        };
        spyOn(ballroomService, "getBallrooms").and.returnValue(of([ballroom1]));
        component.addMode = false;
        component.editMode = false;
        //component.ngOnInit();
        fixture.detectChanges();
        //TODO: changeAddMode programatically or query for buton and click on it
        const button = fixture.debugElement.nativeElement.querySelector('#addNew');
        button.click();
        //component.changeAddMode();
        fixture.detectChanges();
        expect(fixture.debugElement.nativeElement.querySelector('#newBallroomForm')).toBeTruthy();
    });

    //base:
    //TODO: should display description on load
    it("SHOULD display description on load",() => {
        const description = fixture.debugElement.nativeElement.querySelector('#description');
        expect(description.textContent).toBeTruthy();
    });
    //TODO: should display table with ballrooms on load
    
    //addNew:
    //TODO: should display newBallroom form on #addNew button click
    //TODO: should copy provided value from availableDates to hidden div
    //TODO: should display list of ballrooms with added ballroom (service mock invoked) after #save button click
    //TODO: should display list of ballrooms without anything added after #cancel button click
    
    //edit:
    //TODO: should display editBallroom form on #edit button click
    //TODO: should copy provided value from availableDates to hidden div
    //TODO: should display list of ballrooms with edited ballroom (service mock invoked) after #save button click
    //TODO: should display list of ballrooms without anything (service mock not invoked) edited after #cancel button click
});
