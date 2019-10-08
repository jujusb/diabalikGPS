import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { MyData } from './mydata';
import { MycomponentComponent } from './mycomponent/mycomponent.component';

@NgModule({
  declarations: [
    AppComponent,
    MycomponentComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule
  ],
  providers: [MyData],
  bootstrap: [AppComponent]
})
export class AppModule { }
