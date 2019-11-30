import { Component, OnInit } from '@angular/core';
import { MyData } from '../mydata';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';

@Component({
  selector: 'app-diabalik',
  templateUrl: './diabalik.component.html',
  styleUrls: ['./diabalik.component.css']
})
export class DiabalikComponent implements OnInit {
  /* TODO :
- créer un évènement pour la réception de JSON et y afficher le jeu
  */
 // board: List<Case>;

 //ok: boolean;

 // dependency injection: the httpclient is a singleton injected through the constructor
 // Same thing for the router and 'data'.
 // Look at the app.module.ts file to see how the HTTP and router modules have been added to be used here and
 // how 'data' as been configured to be an object that can be injected in the different components of the app.
 // constructor parameters that are defined with a visibility are turned as attributes of the class.
 constructor(private http: HttpClient, private data: MyData) {
   //this.ok = false;
 }

  caseSelected ;
  isACaseSelected;
 
 ngOnInit() {
    this.isACaseSelected=false;
  }
  // Similarly to the 'initialize' of JavaFX, it is
  // called after the view has been initialised
  ngAfterViewInit(): void {
  }

  public clickCase(event : MouseEvent) : void {
    console.log("Click sur la case",1);
    var target=(event.target as HTMLInputElement);
    while(target.className!="case") {
      target=target.parentElement as HTMLInputElement;
    }
    if(!this.isACaseSelected) {
      this.isACaseSelected=true;
      this.caseSelected=target;
      target.style.borderColor='red';
      console.log(target.dataset.x);
    } else {
      //TODO
      //requête REST suivi d'un rafraichissement du board
      this.isACaseSelected=false;
      this.caseSelected.style.borderColor=null;
      var requete = this.http.post('/game/action/move/'+this.caseSelected.dataset.x+'/'+this.caseSelected.dataset.y+'/'+target.dataset.x+'/'+target.dataset.y,{},{});
      //requete.toPromise().catch(this.handleError);
      this.caseSelected=null;
      requete.subscribe((returnedData : any) => {
        console.log(returnedData);
        this.data.receiveJson(JSON.stringify(returnedData));
      });
    }
    console.log(target);
  }

  private handleError(errorResponse : HttpErrorResponse) : any {
    if(errorResponse.error instanceof ErrorEvent) {
      console.error('Client Side Error : ', errorResponse.error.message);
    } else {
      console.error('Serveur Side Error : ', errorResponse);
    }
    //return throwError("There is a problem with the service");
  }

  public killGame() : void {
    var request = this.http.put('/game/kill',{},{});
    request.subscribe(returnedData => console.log(returnedData));
  }

  public undo() : void {
    this.http.put('/game/action/undo',{},{}).subscribe((returnedData : any) => {
      console.log(returnedData);
      this.data.receiveJson(JSON.stringify(returnedData));
    });
  }

  public redo() : void {
    this.http.put('/game/action/redo',{},{}).subscribe((returnedData : any) => {
      console.log(returnedData);
      this.data.receiveJson(JSON.stringify(returnedData));
    });
  }
  public endOfTurn() : void {
    this.http.post('/game/endOfTurn',{},{}).subscribe((returnedData : any) => {
      console.log(returnedData);
      this.data.receiveJson(JSON.stringify(returnedData));
    });
  }
}
