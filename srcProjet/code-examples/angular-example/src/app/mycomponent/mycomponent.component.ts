import { Component, OnInit, ViewChildren, ElementRef, QueryList, AfterViewInit } from '@angular/core';

import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { MyData } from '../mydata';

@Component({
  selector: 'app-mycomponent',
  templateUrl: './mycomponent.component.html',
  styleUrls: ['./mycomponent.component.css']
})
export class MycomponentComponent implements OnInit, AfterViewInit {
  // Refers to the #myobjects declared in the html
  // If not a list, just a ViewChild and an ElementRef
  @ViewChildren('myobjects')
  private myobjects: QueryList<ElementRef>;

  ok: boolean;

  // dependency injection: the httpclient is a singleton injected through the constructor
  // Same thing for the router and 'data'.
  // Look at the app.module.ts file to see how the HTTP and router modules have been added to be used here and
  // how 'data' as been configured to be an object that can be injected in the different components of the app.
  // constructor parameters that are defined with a visibility are turned as attributes of the class.
  constructor(private http: HttpClient, private router: Router, private data: MyData) {
    this.ok = false;
  }

  ngOnInit() {
  }

  // this method comes from the interface AfterViewInit.
  // Similarly to the initialize of JavaFX, it is called after the view has been initialised
  // (and the ViewChild, ViewChildren objects OK)
  ngAfterViewInit(): void {
    // 'myobjects' is not an array: it is a query that queries the html for the elements
    // The content of the query is not the objects but a set of references to the objects (have to use 'nativeElement')
    this.myobjects.forEach(myobject => console.log(myobject.nativeElement));
  }

  public isOk(): boolean {
    return this.ok;
  }

  public setOk(): void {
    this.ok = !this.ok;
  }

  public rightClick(event: MouseEvent): void {
    // this does not work (there is no backend in this example), but it shows how
    // to send an HTTP request (a PUT request here) to a backend server.
    // Note the `string text ${code}` used to build strings.
    // 'as' is used to cast.
    // 'getAttribute('data-x')' gets the value of the attribute data-x of the targeted div (see the HTML file).
    // https://angular.io/guide/http
    this.http.put(`game/myapi/${(event.currentTarget as Element).getAttribute('data-x')}`, {}, {}).
      // This is reactive programming + future object:
      // the request is sent. You can subscribe to (wait for) the returned data as follows:
      subscribe(returnedData => console.log(returnedData));
  }
}
