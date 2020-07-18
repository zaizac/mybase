import { Location } from '@angular/common';
import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';
import { WizardWorkflowService } from './wizard-workflow.service';

@Injectable({
  providedIn: 'root'
})
export class WizardWorkflowGuardService implements CanActivate {
  constructor(private router: Router, private workflowService: WizardWorkflowService, private location: Location) {}

  canActivate(route: ActivatedRouteSnapshot, state?: RouterStateSnapshot): boolean {
    const path: string = route.routeConfig.path;
    return this.verifyWorkFlow(path, route, state.url);
  }

  verifyWorkFlow(path, activeRoute, stateUrl): boolean {
    // If any of the previous steps is invalid, go back to the first invalid step
    const firstPath = this.workflowService.getFirstInvalidStep(path);    
    const paths = stateUrl ? stateUrl.split('/') : [];
    let finalUrl = '';
    if (firstPath.length > 0) {
      if (paths.length) {
        paths.forEach((e, i) => {
          if (i !== paths.length - 1) {
            finalUrl = e ? `${finalUrl}/${e}` : finalUrl;
          } else {
            finalUrl = `${finalUrl}/${firstPath}`;
          }
        });
      }

      const url = finalUrl ? finalUrl : `/${firstPath}`;
      this.router.navigate([url], { skipLocationChange: true });
      paths[paths.length - 1] = this.workflowService.getFirstStep();
      this.location.replaceState(paths.join('/'));

      return false;
    }

    return true;
  }
}
