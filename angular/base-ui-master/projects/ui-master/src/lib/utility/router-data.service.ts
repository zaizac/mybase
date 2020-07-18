import { Injectable } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class RouterDataService {
  constructor(private router: Router, private route: ActivatedRoute) {}

  addRouterData() {
    const path = this.route.routeConfig ? this.route.routeConfig.path : null;
    const finalPath = path || this.router.url;
    if (finalPath) {
      const routerData = JSON.parse(localStorage.getItem('routerData')) || [];
      const stateData = this.router.getCurrentNavigation().extras.state;
      const existingIndex = routerData.findIndex(route => route.path === finalPath);
      if (existingIndex >= 0) {
        routerData[existingIndex].data = stateData;
      } else {
        routerData.push({
          path: finalPath,
          data: stateData
        });
      }
      sessionStorage.setItem('routerData', JSON.stringify(routerData));
    }
  }

  retrieveRouterData() {
    const path = this.route.routeConfig ? this.route.routeConfig.path : null;
    const finalPath = path || this.router.url;
    const routerData = JSON.parse(sessionStorage.getItem('routerData'));
    const filteredRouterData = routerData.filter(data => data.path === finalPath);
    return filteredRouterData.length ? filteredRouterData[0].data : null;
  }

  removeCurrentPageRouterData() {
    const path = this.route.routeConfig ? this.route.routeConfig.path : null;
    const finalPath = path || this.router.url;
    const routerData = JSON.parse(sessionStorage.getItem('routerData'));
    const existingIndex = routerData.findIndex(route => route.path === finalPath);
    routerData.splice(existingIndex, 1);
    sessionStorage.setItem('routerData', JSON.stringify(routerData));
  }

  removeRouterData() {
    sessionStorage.removeItem('routerData');
  }
}
