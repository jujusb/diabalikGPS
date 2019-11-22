import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DiabalikComponent } from './diabalik/diabalik.component';
import { ConfigComponent } from './config/config.component';


const routes: Routes = [
   { path: 'diabalik', component: DiabalikComponent },
   { path: 'config', component: ConfigComponent },
   {path: '**', redirectTo: '/config'},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
