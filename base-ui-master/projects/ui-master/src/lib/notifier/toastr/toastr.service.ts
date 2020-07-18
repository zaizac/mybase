import { Injectable, Inject } from '@angular/core';
import { ToastrComponent } from './toastr.component';
@Injectable({
  providedIn: 'root'
})
export class ToastrComponentsService { 

  constructor(
    private toastr: ToastrComponent,
    ) { }

    /**
     * 
     * @param message 
     */
    success(message){
      this.toastr.simpleToaster('success', 'Success', message, false);
    }

    /**
     * 
     * @param message 
     */
    error(message){
      this.toastr.simpleToaster('error', 'Error', message, false);
    }

    /**
     * 
     * @param message 
     */
    warn(message){
      this.toastr.simpleToaster('warn', 'Warn', message, false);
    }

    /**
     * 
     * @param message 
     */
    info(message){
      this.toastr.simpleToaster('info', 'Info', message, false);
    }

    /**
     * 
     * @param message 
     */
    default(message){
      this.toastr.simpleToaster('default', '', message, false);
    }

    /**
     * 
     * @param type 
     * @param title 
     * @param message 
     * @param enableHtml 
     */
    simpleNotification(type, title, message, enableHtml) {
      this.toastr.simpleToaster(type, title, message, enableHtml);
    }

    /**
     * 
     * @param type 
     * @param title 
     * @param message 
     * @param closeButton 
     * @param timeOut 
     * @param extendedTimeOut 
     * @param disableTimeOut 
     * @param enableHtml 
     * @param progressBar 
     * @param progressAnimation 
     * @param positionClass 
     * @param tapToDismiss 
     */
    customNotification(type, title, message, closeButton, timeOut, extendedTimeOut, 
      disableTimeOut, enableHtml, progressBar, progressAnimation, positionClass, 
      tapToDismiss){
      this.toastr.customToaster(type, title, message, closeButton, timeOut, extendedTimeOut, 
        disableTimeOut, enableHtml, progressBar, progressAnimation, positionClass, 
        tapToDismiss);
    }
}
