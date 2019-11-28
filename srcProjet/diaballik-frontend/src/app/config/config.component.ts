import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Subscriber } from 'rxjs';

@Component({
  selector: 'app-config',
  templateUrl: './config.component.html',
  styleUrls: ['./config.component.css']
})
export class ConfigComponent implements OnInit {
  nameP1 : HTMLInputElement;
  nameP2 : HTMLInputElement;
  checkbox1 : HTMLInputElement;
  checkbox2 : HTMLInputElement;
  iaCheckbox : HTMLInputElement;
  ia : HTMLInputElement;

  constructor(private http: HttpClient) { }

  ngOnInit() {
    this.nameP1 = document.getElementById("namePlayer1") as HTMLInputElement;
    this.nameP2 = document.getElementById("namePlayer2") as HTMLInputElement;
    
    this.checkbox1 = document.getElementById("colorCheckbox1") as HTMLInputElement;
    this.checkbox2 = document.getElementById("colorCheckbox2") as HTMLInputElement;

    this.iaCheckbox = document.getElementById("iaCheckbox") as HTMLInputElement;
    this.ia = document.getElementById("iaType") as HTMLInputElement;
  }

  

  change1(event : MouseEvent){
    this.checkbox2.checked = !this.checkbox1.checked;
  }
  
  change2(event : MouseEvent){
    this.checkbox1.checked = !this.checkbox2.checked;
  }

  launchGame(event : MouseEvent){
    var nameP1 = this.nameP1.value;
    var nameP2 = this.nameP2.value;
    var colorP1 = this.checkbox1.checked;
    var isIa = this.iaCheckbox.checked;
    var request;

    if(isIa){
      var iaType = this.ia.value;
      if(nameP2 == ""){
        request = this.http.put('/game/newPVE/'+nameP1+'/'+colorP1+'/'+iaType,{},{});
      }
      else{
        request = this.http.put('/game/newPVE/'+nameP1+'/'+nameP2+'/'+colorP1+'/'+iaType,{},{});
      }
    }else{
      request = this.http.put('/game/newPVP/'+nameP1+'/'+nameP2+'/'+colorP1,{},{});
    }
    request.subscribe(returnedData => console.log(returnedData));
  }
}
