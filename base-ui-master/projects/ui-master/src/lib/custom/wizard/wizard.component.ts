import { AfterViewChecked, ChangeDetectorRef, Component, Input, OnDestroy, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { delay, filter } from 'rxjs/operators';
import { WizardCommunicationService } from './services/wizard-communication.service';
import { WizardFormDataService } from './services/wizard-form-data.service';
import { WizardWorkflowService } from './services/wizard-workflow.service';
import { StepperConfig } from './stepper.config';

@Component({
  selector: 'ui-wizard',
  templateUrl: './wizard.component.html',
  styleUrls: ['./wizard.component.scss']
})
export class WizardComponent implements OnInit, OnDestroy, AfterViewChecked {
  @Input() stepperConfig: StepperConfig = {};
  title: any;

  defaultNextButton: any = {
    buttonType: 'info',
    text: 'Next',
    display: false,
    icon: '',
    disabled: false
  };
  defaultPrevButton: any = {
    buttonType: 'info',
    text: 'Previous',
    display: false,
    icon: '',
    disabled: false
  };
  defaultOtpButton: any = {
    buttonType: 'info',
    text: 'Submit',
    display: false,
    icon: ''
  };
  nextButton: any = Object.assign({}, this.defaultNextButton);
  prevButton: any = Object.assign({}, this.defaultPrevButton);
  otpButton: any = Object.assign({}, this.defaultOtpButton);
  clickable = true;
  routeEventSubs: Subscription;
  wizCommunicationSubs: Subscription;
  wizStepperSubs: Subscription;
  mode = 'horizontal';
  validColour: any[] = ['primary', 'secondary', 'success', 'info', 'warning', 'danger', 'inactive'];
  statColour: any = {};

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private workflowService: WizardWorkflowService,
    private formDataService: WizardFormDataService,
    private wizardCommunicationService: WizardCommunicationService,
    private cdRef: ChangeDetectorRef
  ) {}

  ngOnInit() {
    let currentRoute = this.route.children.length ? this.route.children[this.route.children.length - 1] : this.route;

    // Check from routing setting if wizard stepper is clickable
    this.stepperConfig.steps = [];
    if (this.route.routeConfig.data && this.route.routeConfig.data.hasOwnProperty('clickable')) {
      this.clickable = this.route.routeConfig.data.clickable;
    }
    this.stepperConfig.clickable = this.clickable;

    // Check from routing wizard stepper mode setting ie: Horizontal or Vertical
    if (this.route.routeConfig.data && this.route.routeConfig.data.mode) {
      this.mode = this.route.routeConfig.data.mode;
    }

    if (this.route.routeConfig.data && this.route.routeConfig.data.statColour) {
      if (this.validColour.includes(this.route.routeConfig.data.statColour.active)) {
        this.statColour.active = this.route.routeConfig.data.statColour.active;
      }
      if (this.validColour.includes(this.route.routeConfig.data.statColour.previous)) {
        this.statColour.previous = this.route.routeConfig.data.statColour.previous;
      }
      if (this.validColour.includes(this.route.routeConfig.data.statColour.next)) {
        this.statColour.next = this.route.routeConfig.data.statColour.next;
      }
      if (this.validColour.includes(this.route.routeConfig.data.statColour.current)) {
        this.statColour.current = this.route.routeConfig.data.statColour.current;
      }
    }

    if (this.route.routeConfig && this.route.routeConfig.children.length && this.route.routeConfig.children[0].data) {
      this.title = this.route.routeConfig.children[0].data.title;
    }

    // Configure stepper base on each child route setting in routing
    this.route.routeConfig.children.forEach((config: any) => {
      if (config.path) {
        this.stepperConfig.steps.push({
          linkUrl: config.path,
          pageName: config.path,
          isView: config.data.isView,
          hide: config.data.hide,
          title: config.data ? config.data.title : '',
          icon: config.data && config.data.stepper ? config.data.stepper.icon : '',
          stepFormName: config.data ? config.data.formName || config.data.pageName : config.path,
          label: config.data && config.data.stepper ? config.data.stepper.label : ''
        });
      }
    });

    this.wizardCommunicationService.stepperConfig = this.stepperConfig.steps;

    const initPath = this.route.routeConfig.data && this.route.routeConfig.data.firstPath;
    // Start initialise wizard form data and workflow
    if (this.stepperConfig.steps.length) {
      this.workflowService.initialiseWorkFlow(this.stepperConfig.steps);
      this.formDataService.initialiseFormData(this.stepperConfig.steps);
      if (currentRoute.routeConfig.data && currentRoute.routeConfig.data.button) {
        this.setButtons(currentRoute.routeConfig.data.button);
      }
      this.router.navigate([initPath || this.stepperConfig.steps[0].linkUrl], {
        relativeTo: this.route,
        skipLocationChange: true
      });
    }

    // Set button base on routing setting every time step page changes
    this.routeEventSubs = this.router.events.pipe(filter(event => event instanceof NavigationEnd)).subscribe(event => {
      currentRoute = this.route.children.length ? this.route.children[this.route.children.length - 1] : this.route;
      if (currentRoute.routeConfig.data) {
        const buttonCfg = Object.assign({}, currentRoute.routeConfig.data.button);
        if (buttonCfg && buttonCfg.next) {
          if (!buttonCfg.next.hasOwnProperty('display')) {
            buttonCfg.next.display = true;
          }
          buttonCfg.next = Object.assign({}, this.defaultNextButton, buttonCfg.next);
        }
        if (buttonCfg && buttonCfg.previous) {
          if (!buttonCfg.previous.hasOwnProperty('display')) {
            buttonCfg.previous.display = true;
          }
          buttonCfg.previous = Object.assign({}, this.defaultPrevButton, buttonCfg.previous);
        }
        this.setButtons(buttonCfg);
        this.title = currentRoute.routeConfig.data.title;
      }
    });

    // Update button after view if button config is set from each page ts file
    this.wizCommunicationSubs = this.wizardCommunicationService.buttonConfigEmitted$.pipe(delay(0)).subscribe(data => {
      this.setButtons(data);
    });

    this.wizStepperSubs = this.wizardCommunicationService.updateStepperEmitted$.pipe(delay(0)).subscribe(data => {
      this.workflowService.updateWorkFlow(data);
      this.formDataService.updateFormData(data);
      this.stepperConfig.steps = this.wizardCommunicationService.stepperConfig;
      this.wizardCommunicationService.updateWizardEvent();
    });
  }

  ngAfterViewChecked() {
    this.cdRef.detectChanges();
  }

  ngOnDestroy() {
    if (this.routeEventSubs) {
      this.routeEventSubs.unsubscribe();
    }

    if (this.wizCommunicationSubs) {
      this.wizCommunicationSubs.unsubscribe();
    }

    if (this.wizStepperSubs) {
      this.wizStepperSubs.unsubscribe();
    }
  }

  private setButtons(buttons: any) {
    if (!!!buttons) {
      this.nextButton.display = false;
      this.prevButton.display = false;
      this.otpButton.display = false;
    } else {
      if (buttons.next) {
        this.setButtonsConfigs(this.nextButton, buttons.next);
      } else {
        this.nextButton.display = false;
      }

      if (buttons.previous) {
        this.setButtonsConfigs(this.prevButton, buttons.previous);
      } else {
        this.prevButton.display = false;
      }

      if (buttons.otp) {
        this.setOtpConfigs(this.otpButton, buttons.otp);
      } else {
        this.otpButton.display = false;
      }
    }
  }

  private setButtonsConfigs(button, config) {
    button = Object.assign(button, config);
    button.display = config.hasOwnProperty('display') ? config.display : true;
  }

  private setOtpConfigs(button, config) {
    button = Object.assign(button, config);
    button.display = config.hasOwnProperty('display') ? config.display : !!config.formGroup;
    button.otpService = config.otpService ? config.otpService : null;
    button.submitted = config.submitted ? config.submitted : null;
    button.required = config.required ? config.required : null;
    button.formControlName = config.formControlName ? config.formControlName : null;
    button.text = config.text ? config.text : null;
    button.redirectURL = config.redirectURL ? config.redirectUrl : null;
    button.email = config.email ? config.email : null;
    button.formGroup = config.formGroup ? config.formGroup : FormGroup;
    button.disabled = config.hasOwnProperty('disabled') ? config.disabled : false;
  }

  nextButtonClick() {
    this.wizardCommunicationService.emitNextBtnClickEvent();
  }

  prevButtonClick() {
    this.wizardCommunicationService.emitPrevBtnClickEvent();
  }

  otpValidated() {
    this.wizardCommunicationService.emitOtpBtnClickEvent();
  }
}
