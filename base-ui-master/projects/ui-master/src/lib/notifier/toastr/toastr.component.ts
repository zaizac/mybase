import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
enum ToasterType {
  Success='success', 
  Error='error', 
  Warn='warn', 
  Info='info', 
  Default='default'
};
@Component({
  selector: 'ui-toastr',
  templateUrl: './toastr.component.html',
  styleUrls: ['./toastr.component.scss']
})
export class ToastrComponent implements OnInit {
 
  constructor(private toastr: ToastrService) { }

  ngOnInit() {
  }

  showToaster(){
    this.simpleToaster('info', 'test title', 
     'Hello, I\'m the toastr message. <a href=\'https://www.google.com\'>Click here</a>', true);
  }

  /**
   * 
   * @param type eg: success, error, warn, info, default
   * @param title 
   * @param message 
   * @param enableHtml eg: true/false
   */
  simpleToaster(type, title, message, enableHtml) {
      this.customToaster(type, title, message, 
      false, 5000, 1000, true, enableHtml, false, 'increasing', 'toast-top-right', true);
  }

  /**
   * 
   * @param type eg: success, error, warn, info, default
   * @param title 
   * @param message 
   * @param closeButton default false
   * @param timeOut  default 5000 milisecs
   * @param extendedTimeOut  default 1000 milisecs
   * @param disableTimeOut  default false
   * @param enableHtml  default false
   * @param progressBar  default false
   * @param progressAnimation  default decreasing
   * @param positionClass  default 'toast-top-right'
   * @param tapToDismiss  default true
   */
  customToaster(type, title, message, closeButton, timeOut, extendedTimeOut, 
    disableTimeOut, enableHtml, progressBar, progressAnimation, positionClass, 
    tapToDismiss) {

      if (ToasterType.Success === type) {
        this.toastr.success(message, title, {
          closeButton: closeButton,
          timeOut: timeOut,
          extendedTimeOut: extendedTimeOut,
          disableTimeOut: disableTimeOut, 
          enableHtml: enableHtml,
          progressBar: progressBar,
          progressAnimation: progressAnimation,
          positionClass: positionClass,
          tapToDismiss: tapToDismiss
        })
      } else if (ToasterType.Error === type) {
        this.toastr.error(message, title, {
          closeButton: closeButton,
          timeOut: timeOut,
          extendedTimeOut: extendedTimeOut,
          disableTimeOut: disableTimeOut, 
          enableHtml: enableHtml,
          progressBar: progressBar,
          progressAnimation: progressAnimation,
          positionClass: positionClass,
          tapToDismiss: tapToDismiss
        })
      } else if (ToasterType.Warn === type) {
        this.toastr.warning(message, title, {
          closeButton: closeButton,
          timeOut: timeOut,
          extendedTimeOut: extendedTimeOut,
          disableTimeOut: disableTimeOut, 
          enableHtml: enableHtml,
          progressBar: progressBar,
          progressAnimation: progressAnimation,
          positionClass: positionClass,
          tapToDismiss: tapToDismiss
        })
      } else if (ToasterType.Info === type) {
        this.toastr.info(message, title, {
          closeButton: closeButton,
          timeOut: timeOut,
          extendedTimeOut: extendedTimeOut,
          disableTimeOut: disableTimeOut, 
          enableHtml: enableHtml,
          progressBar: progressBar,
          progressAnimation: progressAnimation,
          positionClass: positionClass,
          tapToDismiss: tapToDismiss
        })
      } else if (ToasterType.Default === type) {
        this.toastr.show(message, title, {
          closeButton: closeButton,
          timeOut: timeOut,
          extendedTimeOut: extendedTimeOut,
          disableTimeOut: disableTimeOut, 
          enableHtml: enableHtml,
          progressBar: progressBar,
          progressAnimation: progressAnimation,
          positionClass: positionClass,
          tapToDismiss: tapToDismiss
        })
      }
  }

}