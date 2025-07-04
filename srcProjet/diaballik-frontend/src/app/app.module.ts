import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { DiabalikComponent } from './diabalik/diabalik.component';
import { ConfigComponent } from './config/config.component';
import { MyData } from './mydata';

@NgModule({
  declarations: [
    AppComponent,
    DiabalikComponent,
    ConfigComponent
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
