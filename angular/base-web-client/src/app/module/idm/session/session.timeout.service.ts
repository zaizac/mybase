import { Injectable } from '@angular/core';
import { AppComponent } from 'src/app/app.component';

@Injectable({
  providedIn: 'root'
})
export class SessionTimeoutService {
  
  constructor(private app: AppComponent) { }
  
  toggletimer() {
    this.app.toggletimer();
  }
}
