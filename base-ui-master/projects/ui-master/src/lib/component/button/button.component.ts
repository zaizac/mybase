import { Component, EventEmitter, OnInit, Input, Output } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'ui-button',
  templateUrl: './button.component.html',
  styleUrls: ['./button.component.scss']
})
export class ButtonComponent implements OnInit {

  @Input() icon: string;
  @Input() text: string;
  @Input() type: string;
  @Input() buttonType: string;
  @Input() disabled = false;
  @Output() public click: EventEmitter<MouseEvent> = new EventEmitter();
  
  public checkboxGroupForm: FormGroup;

  public radioGroupForm: FormGroup;

  buttonTypeClass = 'btn-default';
  iconClass ='';

  constructor(private formBuilder: FormBuilder) {}

  model = {
    left: true,
    middle: false,
    right: false
  };

  model1 = 1;

  ngOnInit() {
    if (!this.type) {
      this.type = 'button';
    }
    this.checkboxGroupForm = this.formBuilder.group({
      left: true,
      middle: false,
      right: false
    });

    this.radioGroupForm = this.formBuilder.group({
      model: 1
    });

    if (this.buttonType) {
      if (this.buttonType == 'success') {
        this.buttonTypeClass = 'btn-success';
      } else if (this.buttonType == 'warning') {
        this.buttonTypeClass = 'btn-warning';
      } else if (this.buttonType == 'danger') {
        this.buttonTypeClass = 'btn-danger';
      } else if (this.buttonType == 'confirm') {
        this.buttonTypeClass = 'btn-confirm';
      } else if (this.buttonType == 'info') {
        this.buttonTypeClass = 'btn-info';
      } else if (this.buttonType == 'primary') {
        this.buttonTypeClass = 'btn-primary';
      } else if (this.buttonType == 'secondary') {
        this.buttonTypeClass = 'btn-secondary';
      } else if (this.buttonType == 'dark') {
        this.buttonTypeClass = 'btn-dark';
      }
    }

    if (this.buttonType) {
      if (this.buttonType == 'success-ol') {
        this.buttonTypeClass = 'btn-outline-success';
      } else if (this.buttonType == 'warning-ol') {
        this.buttonTypeClass = 'btn-outline-warning';
      } else if (this.buttonType == 'danger-ol') {
        this.buttonTypeClass = 'btn-outline-danger';
      } else if (this.buttonType == 'confirm-ol') {
        this.buttonTypeClass = 'btn-outline-confirm';
      } else if (this.buttonType == 'info-ol') {
        this.buttonTypeClass = 'btn-outline-info';
      } else if (this.buttonType == 'primary-ol') {
        this.buttonTypeClass = 'btn-outline-primary';
      } else if (this.buttonType == 'secondary-ol') {
        this.buttonTypeClass = 'btn-outline-secondary';
      } else if (this.buttonType == 'dark-ol') {
        this.buttonTypeClass = 'btn-outline-dark';
      }
    }
   
    if (this.buttonType) {
      if (this.buttonType == 'success-rd') {
        this.buttonTypeClass = 'btn-rounded btn-success';
      } else if (this.buttonType == 'warning-rd') {
        this.buttonTypeClass = 'btn-rounded btn-warning';
      } else if (this.buttonType == 'danger-rd') {
        this.buttonTypeClass = 'btn-rounded btn-danger';
      }  else if (this.buttonType == 'info-rd') {
        this.buttonTypeClass = 'btn-rounded btn-info';
      } else if (this.buttonType == 'primary-rd') {
        this.buttonTypeClass = 'btn-rounded btn-primary';
      } else if (this.buttonType == 'secondary-rd') {
        this.buttonTypeClass = 'btn-rounded btn-secondary';
      } 
    }

    if (this.buttonType) {
      if (this.buttonType == 'success-rd-ol') {
        this.buttonTypeClass = 'btn-rounded btn-outline-success';
      } else if (this.buttonType == 'warning-rd-ol') {
        this.buttonTypeClass = 'btn-rounded btn-outline-warning';
      } else if (this.buttonType == 'danger-rd-ol') {
        this.buttonTypeClass = 'btn-rounded btn-outline-danger';
      }  else if (this.buttonType == 'info-rd-ol') {
        this.buttonTypeClass = 'btn-rounded btn-outline-info';
      } else if (this.buttonType == 'primary-rd-ol') {
        this.buttonTypeClass = 'btn-rounded btn-outline-primary';
      } else if (this.buttonType == 'secondary-rd-ol') {
        this.buttonTypeClass = 'btn-rounded btn-outline-secondary';
      } 
    }

    // large button
    if (this.buttonType) {
      if (this.buttonType == 'success-lg') {
        this.buttonTypeClass = 'btn-lg btn-success';
      } else if (this.buttonType == 'info-lg') {
        this.buttonTypeClass = 'btn-lg btn-info';
      } else if (this.buttonType == 'primary-lg') {
        this.buttonTypeClass = 'btn-lg btn-primary';
      } else if (this.buttonType == 'secondary-lg') {
        this.buttonTypeClass = 'btn-lg btn-secondary';
      } 
    }

    // small button
    if (this.buttonType) {
      if (this.buttonType == 'success-sm') {
        this.buttonTypeClass = 'btn-sm btn-success';
      } else if (this.buttonType == 'info-sm') {
        this.buttonTypeClass = 'btn-sm btn-info';
      } else if (this.buttonType == 'primary-sm') {
        this.buttonTypeClass = 'btn-sm btn-primary';
      } else if (this.buttonType == 'secondary-sm') {
        this.buttonTypeClass = 'btn-sm btn-secondary';
      } 
    }

    // xs button
    if (this.buttonType) {
      if (this.buttonType == 'success-xs') {
        this.buttonTypeClass = 'btn-xs btn-success';
      } else if (this.buttonType == 'info-xs') {
        this.buttonTypeClass = 'btn-xs btn-info';
      } else if (this.buttonType == 'primary-xs') {
        this.buttonTypeClass = 'btn-xs btn-primary';
      } else if (this.buttonType == 'secondary-xs') {
        this.buttonTypeClass = 'btn-xs btn-secondary';
      } 
    }

    // large rounded button
    if (this.buttonType) {
      if (this.buttonType == 'success-lg-rd') {
        this.buttonTypeClass = 'btn-lg btn-rounded btn-success';
      } else if (this.buttonType == 'info-lg-rd') {
        this.buttonTypeClass = 'btn-lg btn-rounded btn-info';
      } else if (this.buttonType == 'primary-lg-rd') {
        this.buttonTypeClass = 'btn-lg btn-rounded btn-primary';
      } else if (this.buttonType == 'secondary-lg-rd') {
        this.buttonTypeClass = 'btn-lg btn-rounded btn-secondary';
      } 
    }

    // small rounded button
    if (this.buttonType) {
      if (this.buttonType == 'success-sm-rd') {
        this.buttonTypeClass = 'btn-sm btn-rounded btn-success';
      } else if (this.buttonType == 'info-sm-rd') {
        this.buttonTypeClass = 'btn-sm btn-rounded btn-info';
      } else if (this.buttonType == 'primary-sm-rd') {
        this.buttonTypeClass = 'btn-sm btn-rounded btn-primary';
      } else if (this.buttonType == 'secondary-sm-rd') {
        this.buttonTypeClass = 'btn-sm btn-rounded btn-secondary';
      } 
    }

    // xs rounded button
    if (this.buttonType) {
      if (this.buttonType == 'success-xs-rd') {
        this.buttonTypeClass = 'btn-xs btn-rounded btn-success';
      } else if (this.buttonType == 'info-xs-rd') {
        this.buttonTypeClass = 'btn-xs btn-rounded btn-info';
      } else if (this.buttonType == 'primary-xs-rd') {
        this.buttonTypeClass = 'btn-xs btn-rounded btn-primary';
      } else if (this.buttonType == 'secondary-xs-rd') {
        this.buttonTypeClass = 'btn-xs btn-rounded btn-secondary';
      } 
    }

    if (this.buttonType) {
      if (this.buttonType == 'success-blk') {
        this.buttonTypeClass = 'btn-block btn-success';
      } else if (this.buttonType == 'warning-blk') {
        this.buttonTypeClass = 'btn-block btn-warning';
      } else if (this.buttonType == 'danger-blk') {
        this.buttonTypeClass = 'btn-block btn-danger';
      }  else if (this.buttonType == 'info-blk') {
        this.buttonTypeClass = 'btn-block btn-info';
      } else if (this.buttonType == 'primary-blk') {
        this.buttonTypeClass = 'btn-block btn-primary';
      } else if (this.buttonType == 'secondary-blk') {
        this.buttonTypeClass = 'btn-block btn-secondary';
      } 
    }

    if (this.buttonType) {
      if (this.buttonType == 'success-rd-blk') {
        this.buttonTypeClass = 'btn-rounded btn-block btn-success';
      } else if (this.buttonType == 'warning-rd-blk') {
        this.buttonTypeClass = 'btn-rounded btn-block btn-warning';
      } else if (this.buttonType == 'danger-rd-blk') {
        this.buttonTypeClass = 'btn-rounded btn-block btn-danger';
      }  else if (this.buttonType == 'info-rd-blk') {
        this.buttonTypeClass = 'btn-rounded btn-block btn-info';
      } else if (this.buttonType == 'primary-rd-blk') {
        this.buttonTypeClass = 'btn-rounded btn-block btn-primary';
      } else if (this.buttonType == 'secondary-rd-blk') {
        this.buttonTypeClass = 'btn-rounded btn-block btn-secondary';
      } 
    }

    if (this.buttonType) {
      if (this.buttonType == 'success-ol-blk') {
        this.buttonTypeClass = 'btn-block btn-outline-success';
      } else if (this.buttonType == 'warning-ol-blk') {
        this.buttonTypeClass = 'btn-block btn-outline-warning';
      } else if (this.buttonType == 'danger-ol-blk') {
        this.buttonTypeClass = 'btn-block btn-outline-danger';
      }  else if (this.buttonType == 'info-ol-blk') {
        this.buttonTypeClass = 'btn-block btn-outline-info';
      } else if (this.buttonType == 'primary-ol-blk') {
        this.buttonTypeClass = 'btn-block btn-outline-primary';
      } else if (this.buttonType == 'secondary-ol-blk') {
        this.buttonTypeClass = 'btn-block btn-outline-secondary';
      } 
    }

    if (this.buttonType) {
      if (this.buttonType == 'success-rd-ol-blk') {
        this.buttonTypeClass = 'btn-rounded btn-block btn-outline-success';
      } else if (this.buttonType == 'warning-rd-ol-blk') {
        this.buttonTypeClass = 'btn-rounded btn-block btn-outline-warning';
      } else if (this.buttonType == 'danger-rd-ol-blk') {
        this.buttonTypeClass = 'btn-rounded btn-block btn-outline-danger';
      }  else if (this.buttonType == 'info-rd-ol-blk') {
        this.buttonTypeClass = 'btn-rounded btn-block btn-outline-info';
      } else if (this.buttonType == 'primary-rd-ol-blk') {
        this.buttonTypeClass = 'btn-rounded btn-block btn-outline-primary';
      } else if (this.buttonType == 'secondary-rd-ol-blk') {
        this.buttonTypeClass = 'btn-rounded btn-block btn-outline-secondary';
      } 
    }

    if (this.buttonType) {
      if (this.buttonType == 'success-blk-lg') {
        this.buttonTypeClass = 'btn-block btn-lg btn-success';
      } else if (this.buttonType == 'warning-blk-lg') {
        this.buttonTypeClass = 'btn-block btn-lg btn-warning';
      } else if (this.buttonType == 'danger-blk-lg') {
        this.buttonTypeClass = 'btn-block btn-lg btn-danger';
      }  else if (this.buttonType == 'info-blk-lg') {
        this.buttonTypeClass = 'btn-block btn-lg btn-info';
      } else if (this.buttonType == 'primary-blk-lg') {
        this.buttonTypeClass = 'btn-block btn-lg btn-primary';
      } else if (this.buttonType == 'secondary-blk-lg') {
        this.buttonTypeClass = 'btn-block btn-lg btn-secondary';
      } 
    }

    if (this.buttonType) {
      if (this.buttonType == 'warning-wave') {
        this.buttonTypeClass = 'btn-warning waves-effect waves-light';
      } else if (this.buttonType == 'danger-wave') {
        this.buttonTypeClass = 'btn-danger waves-effect waves-light';
      }  else if (this.buttonType == 'info-wave') {
        this.buttonTypeClass = 'btn-info waves-effect waves-light';
      } else if (this.buttonType == 'primary-wave') {
        this.buttonTypeClass = 'btn-primary waves-effect waves-light';
      } else if (this.buttonType == 'secondary-wave') {
        this.buttonTypeClass = 'btn-secondary waves-effect waves-light';
      } 
    }

    if (this.buttonType) {
      if (this.buttonType == 'warning-wave-ol') {
        this.buttonTypeClass = 'btn-outline-warning waves-effect waves-light';
      } else if (this.buttonType == 'danger-wave-ol') {
        this.buttonTypeClass = 'btn-outline-danger waves-effect waves-light';
      }  else if (this.buttonType == 'info-wave-ol') {
        this.buttonTypeClass = 'btn-outline-info waves-effect waves-light';
      } else if (this.buttonType == 'primary-wave-ol') {
        this.buttonTypeClass = 'btn-outline-primary waves-effect waves-light';
      } else if (this.buttonType == 'secondary-wave-ol') {
        this.buttonTypeClass = 'btn-outline-secondary waves-effect waves-light';
      } 
    }

    if (this.buttonType) {
      if (this.buttonType == 'success-ol-rd') {
        this.buttonTypeClass = 'btn-outline-success btn-rounded';
      } else if (this.buttonType == 'warning-ol-rd') {
        this.buttonTypeClass = 'btn-outline-warning btn-rounded';
      } else if (this.buttonType == 'danger-ol-rd') {
        this.buttonTypeClass = 'btn-outline-danger btn-rounded';
      }  else if (this.buttonType == 'info-ol-rd') {
        this.buttonTypeClass = 'btn-outline-info btn-rounded';
      } else if (this.buttonType == 'primary-ol-rd') {
        this.buttonTypeClass = 'btn-outline-primary btn-rounded';
      } else if (this.buttonType == 'secondary-ol-rd') {
        this.buttonTypeClass = 'btn-outline-secondary btn-rounded';
      } 
    }

    // button circle
    if (this.buttonType) {
      if (this.buttonType == 'success-cl') {
        this.buttonTypeClass = 'btn-success btn-circle';
      } else if (this.buttonType == 'warning-cl') {
        this.buttonTypeClass = 'btn-warning btn-circle';
      } else if (this.buttonType == 'danger-cl') {
        this.buttonTypeClass = 'btn-danger btn-circle';
      }  else if (this.buttonType == 'info-cl') {
        this.buttonTypeClass = 'btn-info btn-circle';
      } else if (this.buttonType == 'primary-cl') {
        this.buttonTypeClass = 'btn-primary btn-circle';
      } else if (this.buttonType == 'secondary-cl') {
        this.buttonTypeClass = 'btn-secondary btn-circle';
      } 
    }

    // button circle large
    if (this.buttonType) {
      if (this.buttonType == 'success-cl-lg') {
        this.buttonTypeClass = 'btn-success btn-circle btn-lg';
      } else if (this.buttonType == 'warning-cl-lg') {
        this.buttonTypeClass = 'btn-warning btn-circle btn-lg';
      } else if (this.buttonType == 'danger-cl-lg') {
        this.buttonTypeClass = 'btn-danger btn-circle btn-lg';
      }  else if (this.buttonType == 'info-cl-lg') {
        this.buttonTypeClass = 'btn-info btn-circle btn-lg';
      } else if (this.buttonType == 'primary-cl-lg') {
        this.buttonTypeClass = 'btn-primary btn-circle btn-lg';
      } else if (this.buttonType == 'secondary-cl-lg') {
        this.buttonTypeClass = 'btn-secondary btn-circle btn-lg';
      } 
    }

    // button circle xl
    if (this.buttonType) {
      if (this.buttonType == 'success-cl-xl') {
        this.buttonTypeClass = 'btn-success btn-circle btn-xl';
      } else if (this.buttonType == 'warning-cl-xl') {
        this.buttonTypeClass = 'btn-warning btn-circle btn-xl';
      } else if (this.buttonType == 'info-cl-xl') {
        this.buttonTypeClass = 'btn-info btn-circle btn-xl';
      } else if (this.buttonType == 'primary-cl-xl') {
        this.buttonTypeClass = 'btn-primary btn-circle btn-xl';
      } else if (this.buttonType == 'secondary-cl-xl') {
        this.buttonTypeClass = 'btn-secondary btn-circle btn-xl';
      } 
    }

    // social button
    if (this.buttonType) {
      if (this.buttonType == 'facebook-wave') {
        this.buttonTypeClass = 'btn-facebook waves-effect waves-light';
      } else if (this.buttonType == 'twitter-wave') {
        this.buttonTypeClass = 'btn-twitter waves-effect waves-light';
      }  else if (this.buttonType == 'googleplus-wave') {
        this.buttonTypeClass = 'btn-googleplus waves-effect waves-light';
      } else if (this.buttonType == 'linkedin-wave') {
        this.buttonTypeClass = 'btn-linkedin waves-effect waves-light';
      } else if (this.buttonType == 'instagram-wave') {
        this.buttonTypeClass = 'btn-instagram waves-effect waves-light';
      } else if (this.buttonType == 'pinterest-wave') {
        this.buttonTypeClass = 'btn-pinterest waves-effect waves-light';
      } else if (this.buttonType == 'dribbble-wave') {
        this.buttonTypeClass = 'btn-dribbble waves-effect waves-light';
      } else if (this.buttonType == 'youtube-wave') {
        this.buttonTypeClass = 'btn-youtube waves-effect waves-light';
      }
    }

    // social button circle
    if (this.buttonType) {
      if (this.buttonType == 'facebook-wave-cl') {
        this.buttonTypeClass = 'btn-facebook waves-effect btn-circle waves-light';
      } else if (this.buttonType == 'twitter-wave-cl') {
        this.buttonTypeClass = 'btn-twitter waves-effect btn-circle waves-light';
      }  else if (this.buttonType == 'googleplus-wave-cl') {
        this.buttonTypeClass = 'btn-googleplus waves-effect btn-circle waves-light';
      } else if (this.buttonType == 'linkedin-wave-cl') {
        this.buttonTypeClass = 'btn-linkedin waves-effect btn-circle waves-light';
      } else if (this.buttonType == 'instagram-wave-cl') {
        this.buttonTypeClass = 'btn-instagram waves-effect btn-circle waves-light';
      } else if (this.buttonType == 'pinterest-wave-cl') {
        this.buttonTypeClass = 'btn-pinterest waves-effect btn-circle waves-light';
      } else if (this.buttonType == 'dribbble-wave-cl') {
        this.buttonTypeClass = 'btn-dribbble waves-effect btn-circle waves-light';
      } else if (this.buttonType == 'youtube-wave-cl') {
        this.buttonTypeClass = 'btn-youtube waves-effect btn-circle waves-light';
      }
    }

    // social button rounded
    if (this.buttonType) {
      if (this.buttonType == 'facebook-wave-rd') {
        this.buttonTypeClass = 'btn-facebook waves-effect btn-rounded waves-light';
      } else if (this.buttonType == 'twitter-wave-rd') {
        this.buttonTypeClass = 'btn-twitter waves-effect btn-rounded waves-light';
      }  else if (this.buttonType == 'googleplus-wave-rd') {
        this.buttonTypeClass = 'btn-googleplus waves-effect btn-rounded waves-light';
      } else if (this.buttonType == 'linkedin-wave-rd') {
        this.buttonTypeClass = 'btn-linkedin waves-effect btn-rounded waves-light';
      } else if (this.buttonType == 'instagram-wave-rd') {
        this.buttonTypeClass = 'btn-instagram waves-effect btn-rounded waves-light';
      } else if (this.buttonType == 'pinterest-wave-rd') {
        this.buttonTypeClass = 'btn-pinterest waves-effect btn-rounded waves-light';
      } else if (this.buttonType == 'dribbble-wave-rd') {
        this.buttonTypeClass = 'btn-dribbble waves-effect btn-rounded waves-light';
      } else if (this.buttonType == 'youtube-wave-rd') {
        this.buttonTypeClass = 'btn-youtube waves-effect btn-rounded waves-light';
      }
    }

    // icon

    if (this.icon) {
      if (this.icon == 'check') {
        this.iconClass = 'fa fa-check';
      } else if (this.icon == 'heart') {
        this.iconClass = 'fa fa-heart';
      } else if (this.icon == 'envelope') {
        this.iconClass = 'fa fa-envelope-o';
      } else if (this.icon == 'list') {
        this.iconClass = 'fa fa-list';
      } else if (this.icon == 'link') {
        this.iconClass = 'fa fa-link';
      } else if (this.icon == 'times') {
        this.iconClass = 'fa fa-times';
      } else if (this.icon == 'align-left') {
        this.iconClass = 'fa fa-align-left';
      } else if (this.icon == 'align-justify') {
        this.iconClass = 'fa fa-align-justify';
      } else if (this.icon == 'align-right') {
        this.iconClass = 'fa fa-align-right';
      } else if (this.icon == 'backward') {
        this.iconClass = 'fa fa-fast-backward';
      } else if (this.icon == 'play') {
        this.iconClass = 'fa fa-play';
      } else if (this.icon == 'forward') {
        this.iconClass = 'fa fa-fast-forward';
      }
    }

  }

  onButtonClick(event: MouseEvent) {
    if (this.disabled) { event.stopPropagation(); }
    // this if condition applies to any child object within button to avoid multiple click event
    if (event.target !== event.currentTarget) { return; }
  }

  onIconSpanClick(event: MouseEvent) {
    if(this.disabled) { event.stopPropagation(); }
  }
}
