import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, NavigationEnd, Router, ActivatedRouteSnapshot, UrlSegment } from '@angular/router';
import { distinctUntilChanged, filter } from 'rxjs/operators';
import { IBreadCrumb } from './breadcrumb.interface';


@Component({
  selector: 'ui-breadcrumb',
  templateUrl: './breadcrumb.component.html',
  styleUrls: ['./breadcrumb.component.scss']
})
export class BreadcrumbComponent implements OnInit {

  public breadcrumbs: IBreadCrumb[];
  public pageInfo: string;

  constructor(private readonly router: Router,
    private activatedRoute: ActivatedRoute,
  ) {
    this.breadcrumbs = this.buildBreadCrumb(this.activatedRoute.root);
    this.pageInfo = this.getPageInfo(this.breadcrumbs);
  }

  getPageInfo(breadcrumbs: IBreadCrumb[] = []): any {
    return breadcrumbs.length === 0 ? '' : breadcrumbs[breadcrumbs.length - 1].label;
  }

  /**
   * Recursively build breadcrumb according to activated route.
   * @param route
   * @param url
   * @param breadcrumbs
   */
  buildBreadCrumb(route: ActivatedRoute, breadcrumbs: IBreadCrumb[] = []): IBreadCrumb[] {
    // ... implementation of buildBreadCrumb
    // If no routeConfig is avalailable we are on the root path
    const label = route.routeConfig && route.routeConfig.data ? route.routeConfig.data.breadcrumb : '';
    const path = route.routeConfig && route.routeConfig.data ? route.routeConfig.path : '';   

    // TODO: Requires deep investigation if it is required to cater dynamic route
    // If the route is dynamic route such as ':id', remove it
    // const lastRoutePart = path.split('/').pop();
    // const isDynamicRoute = lastRoutePart.startsWith(':');
    // if (isDynamicRoute && !!route.snapshot) {
    //   const paramName = lastRoutePart.split(':')[1];
    //   path = path.replace(lastRoutePart, route.snapshot.params[paramName]);
    //   label = route.snapshot.params[paramName];
    // }

    // Get parent url
    const url = this.getParentPath(route.parent);

    // In the routeConfig the complete path is not available,
    // so we rebuild it each time
    const nextUrl = path ? `${url}/${path}` : url;

    const breadcrumb: IBreadCrumb = {
      label: label,
      url: nextUrl,
    };
    
    // Only adding route with non-empty label
    const newBreadcrumbs = breadcrumb.label ? [...breadcrumbs, breadcrumb] : [...breadcrumbs];
    if (route.firstChild) {
      // If we are not on our current path yet,
      // there will be more children to look after, to build our breadcumb
      return this.buildBreadCrumb(route.firstChild, newBreadcrumbs);
    }
    return newBreadcrumbs;
  }

  getParentPath(parentRoute: ActivatedRoute) {
    if(parentRoute == undefined) {
      return;
    }
    var newParentPath = '';    
    if(parentRoute.url) {
      parentRoute.url.subscribe((urlPath) => {
        if(urlPath.length != 0) {
          newParentPath = "/" + urlPath;
        }
      });
      if(newParentPath == '/') {
        newParentPath = '';
      }
    }
    if(parentRoute.parent && parentRoute.parent.url) {
      newParentPath = this.getParentPath(parentRoute.parent) + newParentPath;
    }
    return newParentPath;
  }

  ngOnInit() {
    this.router.events
      .pipe(filter(event => event instanceof NavigationEnd), distinctUntilChanged())
      .subscribe(() => {
        this.breadcrumbs = this.buildBreadCrumb(this.activatedRoute.root);
        this.pageInfo = this.getPageInfo(this.breadcrumbs);
      });
  }

}