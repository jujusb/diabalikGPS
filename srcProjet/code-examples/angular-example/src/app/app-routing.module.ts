import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MycomponentComponent } from './mycomponent/mycomponent.component';


const routes: Routes = [
  // { path: 'path1', component: AComponent },
  { path: 'foo', component: MycomponentComponent },
  { path: '',
    redirectTo: '/foo',
    pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
