import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-config',
  templateUrl: './config.component.html',
  styleUrls: ['./config.component.css']
})
export class ConfigComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

  checkbox1;
  checkbox2;

  change1(){
    if(this.checkbox1){
      this.checkbox2 = false;
      this.checkbox1 = false;
      }
    else{
      this.checkbox2 = false;
      this.checkbox1 = true;
    }
  }
  
  change2(){
    if(this.checkbox2){
      this.checkbox2 = false;
      this.checkbox1 = false;
    } else{
      this.checkbox2 = true;
      this.checkbox1 = false;
    }
  }
}
