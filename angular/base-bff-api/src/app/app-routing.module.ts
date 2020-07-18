import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './module/auth/login/login.component';
import { AppComponent } from './app.component';
import { HomeComponent } from './module/common/home/home.component';

const routes: Routes = [
   {path: '', redirectTo: 'login', pathMatch: 'full' },
   {path:'login', component:LoginComponent},
   {path:'home', component:HomeComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
