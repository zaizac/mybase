import { Component, OnInit, OnDestroy } from '@angular/core';
import { Location } from '@angular/common';
import { ReferenceService } from 'bff-api';
import { Subscription } from 'rxjs';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'ui-refreshstatic',
  templateUrl: './refreshstatic.component.html'
})
export class RefreshstaticComponent implements OnInit, OnDestroy {

  navigationSubscription: Subscription;
  staticlistType: string;

  constructor(private location: Location,
    private referenceService: ReferenceService,
    private route: ActivatedRoute) {
    this.route.queryParams.subscribe(params => {
      this.staticlistType = params.staticlistType;
    });
  }

  ngOnInit() {
    // refresh static references and redirect previous page
    this.navigationSubscription = this.referenceService.refreshStaticRef(null).subscribe(resp => { this.location.back(); });
  }

  ngOnDestroy() {
    // avoid memory leaks here by cleaning up after ourselves. If we
    // don't then we might continue to run our refreshStaticRef()
    if (this.navigationSubscription) {
      this.navigationSubscription.unsubscribe();
    }
  }

}
