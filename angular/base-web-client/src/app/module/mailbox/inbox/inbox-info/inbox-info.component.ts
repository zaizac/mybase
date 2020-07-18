import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RouterDataService } from 'ui-master';

@Component({
  selector: 'ui-inbox-info',
  templateUrl: './inbox-info.component.html',
  styleUrls: ['./inbox-info.component.scss']
})
export class InboxInfoComponent implements OnInit {
  data: any = {};

  constructor(private router: Router, private route: ActivatedRoute, private routerData: RouterDataService) {
    const stateData = this.router.getCurrentNavigation().extras.state;
    if (stateData) {
      this.data = stateData;
      routerData.addRouterData();
    } else {
      this.data = routerData.retrieveRouterData();
    }
  }

  ngOnInit() {
  }

}
