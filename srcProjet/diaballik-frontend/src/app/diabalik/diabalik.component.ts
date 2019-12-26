import { Component, OnInit, Inject } from '@angular/core';
import { MyData } from '../mydata';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
//import{ MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

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
 constructor(private http: HttpClient, private data: MyData) {//, private dialog: MatDialog) {
   //this.ok = false;
 }

  caseSelected ;
  isACaseSelected;
  board;
  isFirstTurn=true;
 
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
      if(this.data.storage.gameBoard.board[7 * parseInt(target.dataset.y,10) + parseInt(target.dataset.x,10)]!=null) {
        if(this.data.storage.gameBoard.board[7 * parseInt(target.dataset.y,10) + parseInt(target.dataset.x,10)].player==(this.data.storage.currentPlayer.colour? "white" : "black")) {
          this.isACaseSelected=true;
          this.caseSelected=target;
          target.style.borderColor='lime';
          console.log(target.dataset.x);

          if(this.isFirstTurn) {
            var tar=event.composedPath() as any ;
            this.board=tar[2].children as HTMLCollection;
            this.isFirstTurn=false;
          }
          console.log(this.board);
          var req = this.http.get('/game/getPossibleMovesFrom/'+target.dataset.x+'/'+target.dataset.y);
          req.subscribe((returnData : any)=>{
            this.data.receiveListOfMovesJson(returnData);
            console.log(this.data.listOfMoves);
            var i ;
            for(i = 0; i<this.data.listOfMoves.list.length ; i++) {
              var x = this.data.listOfMoves.list[i].target.posX;
              var y = 6-this.data.listOfMoves.list[i].target.posY;
              console.log("move");
              console.log(this.board[7*y+x]);
              this.board[7*y+x].style.background="blue";
            }
          });
       }
      }

    } else {
      //requête REST suivi d'un rafraichissement du board
      this.isACaseSelected=false;
      this.caseSelected.style.borderColor=null;
      var requete = this.http.post('/game/action/move/'+this.caseSelected.dataset.x+'/'+this.caseSelected.dataset.y+'/'+target.dataset.x+'/'+target.dataset.y,{},{});
      this.caseSelected=null;
      requete.subscribe((returnedData : any) => {
        //console.log(returnedData);
        this.data.receiveJson(returnedData);
      });
      var i;
      for(i = 0; i<this.data.listOfMoves.list.length ; i++) {
        var x = this.data.listOfMoves.list[i].target.posX;
        var y = 6-this.data.listOfMoves.list[i].target.posY;
        console.log("move");
        console.log(this.board[7*y+x]);
        this.board[7*y+x].style.background="rosybrown";
      }
    }
    console.log(target);
  }

  /**private winner() {
    const dialogRef = this.dialog.open(DiabalikWinnerDialog, {
      width: '250px'
    });

    dialogRef.close();
    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }*/

  public killGame() : void {
    var request = this.http.put('/game/kill',{},{});
    request.subscribe((returnedData : any) => 
      {console.log(returnedData);
      this.data.receiveJson(returnedData);
      });
  }

  public undo() : void {
    this.http.put('/game/action/undo',{},{}).subscribe((returnedData : any) => {
      console.log(returnedData);
      this.data.receiveJson(returnedData);
    });
  }

  public redo() : void {
    this.http.put('/game/action/redo',{},{}).subscribe((returnedData : any) => {
      console.log(returnedData);
      this.data.receiveJson(returnedData);
    });
  }
  public endOfTurn() : void {
    this.http.post('/game/endOfTurn',{},{}).subscribe((returnedData : any) => {
      console.log(returnedData);
      this.data.receiveJson(returnedData);
    });
  }

  public redemarrerGame() : void {
    var request;
    var nameP1 = this.data.storage.player1.name;
    var colorP1 = this.data.storage.player1.colour;
    var nameP2 = this.data.storage.player2.name;
    if(this.data.storage.player2.type=='aiPlayer') {
      var iaType=this.data.storage.player2.algo ;
      request = this.http.post('/game/newPvE/'+nameP1+'/'+colorP1+'/'+iaType,{},{});
    }else{
      request = this.http.post('/game/newPvP/'+nameP1+'/'+nameP2+'/'+colorP1,{},{});
    }
    request.subscribe(
      returnedData => {
        console.log(returnedData);
        this.data.receiveJson(returnedData);
      });
  }
}

/*
@Component({
  selector: 'diabalik.component.dialog',
  templateUrl: 'diabalik.component.dialog.html',
})
export class DiabalikWinnerDialog {

  constructor(
    public dialogRef: MatDialogRef<DiabalikWinnerDialog>,
    private http : HttpClient,
    public data: MyData) {}

  public redemarrerGame() : void {
    var request;
    var nameP1 = this.data.storage.player1.name;
    var colorP1 = this.data.storage.player1.color;
    var nameP2 = this.data.storage.player2.name;
    if(this.data.storage.player2.type=='aiPlayer') {
      var iaType=this.data.storage.player2.algo ;
      request = this.http.post('/game/newPvE/'+nameP1+'/'+colorP1+'/'+iaType,{},{});
    }else{
      request = this.http.post('/game/newPvP/'+nameP1+'/'+nameP2+'/'+colorP1,{},{});
    }
    request.subscribe(
      returnedData => {
        console.log(returnedData);
        this.data.receiveJson(returnedData);
      });
  }

  public killGame() : void {
    var request = this.http.put('/game/kill',{},{});
    request.subscribe(returnedData => console.log(returnedData));
  }
}*/