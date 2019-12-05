import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { MyData } from '../mydata';

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
  //ia : HTMLSelectElement ;
  mylist: Array<string>;

  constructor(private http: HttpClient, private data : MyData) { }

  ngOnInit() {
    this.nameP1 = document.getElementById("namePlayer1") as HTMLInputElement;
    this.nameP2 = document.getElementById("namePlayer2") as HTMLInputElement;
    
    this.checkbox1 = document.getElementById("colorCheckbox1") as HTMLInputElement;
    this.checkbox2 = document.getElementById("colorCheckbox2") as HTMLInputElement;

    this.iaCheckbox = document.getElementById("iaCheckbox") as HTMLInputElement;
    this.mylist = ["NOOB","STARTING","PROGRESSIVE","MONTECARLO"];
  }

  

  change1(event : MouseEvent){
    this.checkbox2.checked = !this.checkbox1.checked;
  }
  
  change2(event : MouseEvent){
    this.checkbox1.checked = !this.checkbox2.checked;
  }

  launchGame(event : MouseEvent, IASelected : any){
    var nameP1 = this.nameP1.value;
    var nameP2 = this.nameP2.value;
    var colorP1 = this.checkbox1.checked;
    var isIa = this.iaCheckbox.checked;
    var request;
    console.log(nameP1);
    console.log(nameP2+' '+nameP2.length);
    if(isIa){
      var IASelectedIndex= IASelected.selectedIndex;
      console.log(IASelectedIndex+' '+ this.mylist[IASelectedIndex]);
      var iaType=this.mylist[IASelectedIndex] ;
      if(nameP2=="") {
        request = this.http.post('/game/newPvE/'+nameP1+'/'+colorP1+'/'+iaType,{},{});
      }
      else{
        request = this.http.post('/game/newPvE/'+nameP1+'/'+nameP2+'/'+colorP1+'/'+iaType,{},{});
      }
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