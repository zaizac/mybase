import { AfterViewInit, Component, Input, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { delay } from 'rxjs/operators';
import { HorizontalWizardDirective } from '../directives/horizontal-wizard.directive';
import { WizardCommunicationService } from '../services/wizard-communication.service';
import { WizardFormDataService } from '../services/wizard-form-data.service';

@Component({
  selector: 'ui-wizard-navbar',
  // changeDetection: ChangeDetectionStrategy.OnPush,
  templateUrl: './wizard-navbar.component.html',
  styleUrls: ['./wizard-navbar.component.scss']
})
export class WizardNavbarComponent implements OnInit, OnDestroy, AfterViewInit {
  @Input() stepperConfig: any;
  @Input() mode = '';
  @ViewChild(HorizontalWizardDirective) wizDirective: HorizontalWizardDirective;
  @Input() statColour: any = {
    previous: 'success',
    current: 'primary',
    next: 'inactive'
  };
  page = '';
  pageIndex = 0;
  currentIndex = 0;
  statActive = 'nav-link active';
  statPrev = 'nav-link';
  statNext = 'nav-link';
  statCurrent = 'nav-link';
  wizCommunicationSubs: Subscription;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private wizardCommunicationService: WizardCommunicationService,
    private formDataService: WizardFormDataService
  ) {}

  ngOnInit() {
    if (this.stepperConfig.steps.length) {
      this.page = this.stepperConfig.steps[0].pageName;
      this.pageIndex = 0;
    }
    this.currentIndex = this.formDataService.getStepperInitialStepIndex(0);

    this.router.events.subscribe(event => {
      let currentRoute = this.route.root;
      while (currentRoute.children[0] !== undefined) {
        currentRoute = currentRoute.children[0];
      }
      this.page = currentRoute.snapshot.routeConfig.path;
      this.pageIndex = this.stepperConfig.steps.findIndex(step => step.pageName === this.page);
      if (this.currentIndex < this.formDataService.getStepperInitialStepIndex(this.pageIndex)) {
        this.currentIndex = this.formDataService.getStepperInitialStepIndex(this.pageIndex);
      }
    });

    this.setStatActiveColour();
    this.setStatNextColour();
    this.setStatPrevColour();
    this.setStatCurrentColour();

    // Update current status index if user change the value
    this.wizCommunicationSubs = this.wizardCommunicationService.currentStatIndEmitted$
      .pipe(delay(0))
      .subscribe((data: any) => {
        this.currentIndex = data;
      });

    this.wizardCommunicationService.updateWizardEmitted$.pipe(delay(0)).subscribe(() => {
      this.wizDirective.updateStepper();
    });
  }

  ngOnDestroy() {
    if (this.wizCommunicationSubs) {
      this.wizCommunicationSubs.unsubscribe();
    }
  }

  ngAfterViewInit() {
    if (this.wizDirective) {
      this.wizDirective.updateStepper();
    }
  }

  setStatActiveColour() {
    if (this.statColour.active === 'primary') {
      this.statActive = 'nav-link active-primary';
    } else if (this.statColour.active === 'danger') {
      this.statActive = 'nav-link active-danger';
    } else if (this.statColour.active === 'warning') {
      this.statActive = 'nav-link active-warning';
    } else if (this.statColour.active === 'success') {
      this.statActive = 'nav-link active-success';
    } else if (this.statColour.active === 'secondary') {
      this.statActive = 'nav-link active-secondary';
    } else if (this.statColour.active === 'info') {
      this.statActive = 'nav-link active';
    } else if (this.statColour.active === 'inactive') {
      this.statActive = 'nav-link active-default';
    } else {
      this.statActive = 'nav-link active';
    }
  }

  setStatCurrentColour() {
    if (this.statColour.current === 'primary') {
      this.statCurrent = 'nav-link active-primary';
    } else if (this.statColour.current === 'danger') {
      this.statCurrent = 'nav-link active-danger';
    } else if (this.statColour.current === 'warning') {
      this.statCurrent = 'nav-link active-warning';
    } else if (this.statColour.current === 'success') {
      this.statCurrent = 'nav-link active-success';
    } else if (this.statColour.current === 'secondary') {
      this.statCurrent = 'nav-link active-secondary';
    } else if (this.statColour.current === 'info') {
      this.statCurrent = 'nav-link active-active';
    } else if (this.statColour.current === 'inactive') {
      this.statCurrent = 'nav-link active-default';
    } else {
      this.statCurrent = 'nav-link active-warning';
    }
  }

  setStatNextColour() {
    if (this.statColour.next === 'primary') {
      this.statNext = 'nav-link primary';
    } else if (this.statColour.next === 'danger') {
      this.statNext = 'nav-link danger';
    } else if (this.statColour.next === 'warning') {
      this.statNext = 'nav-link warning';
    } else if (this.statColour.next === 'success') {
      this.statNext = 'nav-link success';
    } else if (this.statColour.next === 'secondary') {
      this.statNext = 'nav-link secondary';
    } else if (this.statColour.next === 'info') {
      this.statNext = 'nav-link';
    } else if (this.statColour.next === 'inactive') {
      this.statNext = 'nav-link default';
    } else {
      this.statNext = 'nav-link default';
    }
  }

  setStatPrevColour() {
    if (this.statColour.previous === 'primary') {
      this.statPrev = 'nav-link active-primary';
    } else if (this.statColour.previous === 'danger') {
      this.statPrev = 'nav-link active-danger';
    } else if (this.statColour.previous === 'warning') {
      this.statPrev = 'nav-link active-warning';
    } else if (this.statColour.previous === 'success') {
      this.statPrev = 'nav-link active-success';
    } else if (this.statColour.previous === 'secondary') {
      this.statPrev = 'nav-link active-secondary';
    } else if (this.statColour.previous === 'info') {
      this.statPrev = 'nav-link';
    } else if (this.statColour.previous === 'inactive') {
      this.statPrev = 'nav-link active-default';
    } else {
      this.statPrev = 'nav-link active-success';
    }
  }
}
