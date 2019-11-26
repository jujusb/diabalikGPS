import { Component, OnInit } from '@angular/core';
import { MyData } from '../mydata';

@Component({
  selector: 'app-diabalik',
  templateUrl: './diabalik.component.html',
  styleUrls: ['./diabalik.component.css']
})
export class DiabalikComponent implements OnInit {
  /* TODO :
- remplir d'attributs décrivant le jeu cette classe
- créer une ou des classes pour aider au premier point (ex : la classe case comme dans la ligne commentée du dessous)
- dans le onInit, afficher les pions soit en touchant au HTML soit en utilisant ce qui est indiqué diapo 28
- créer un parser json pour remplir les attributs
- créer un évènement pour la réception de JSON et y afficher le jeu
  */
 // board: List<Case>;

 //ok: boolean;

 // dependency injection: the httpclient is a singleton injected through the constructor
 // Same thing for the router and 'data'.
 // Look at the app.module.ts file to see how the HTTP and router modules have been added to be used here and
 // how 'data' as been configured to be an object that can be injected in the different components of the app.
 // constructor parameters that are defined with a visibility are turned as attributes of the class.
 constructor(private data: MyData) {
   //this.ok = false;
 }

  ngOnInit() {
  }

}
