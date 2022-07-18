import { Component, Input } from "@angular/core";
import { Router } from "@angular/router";
import { SongService } from "./songs.service";

@Component({
    selector: "songs-component",
    templateUrl: "./songs.component.html"
})
export class SongsComponent {
    @Input() image : string = "/assets/songs.jpg";
    public songs;
    public addMode = false;

    constructor(private songService : SongService, private router : Router){}

    ngOnInit() {
        this.getSongs();
    }

    changeAddMode = function() {
        this.addMode = !this.addMode; 
    }

    submitForm = function(formValues) {
        this.songService.addSong(formValues).subscribe( () => {
            this.getSongs();
            this.changeAddMode();
            this.router.navigate(['/song']);
        }); 
    }
    
    getSongs() {
        this.songService.getSongs()
            .subscribe(songs => this.songs = songs);
    }


    removeSong = function(song) {
        this.songService.removeSong(song).subscribe(() => {this.getSongs()});
    }
}