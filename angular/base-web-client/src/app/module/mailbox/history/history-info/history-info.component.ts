import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RouterDataService } from 'ui-master';

@Component({
  selector: 'ui-history-info',
  templateUrl: './history-info.component.html',
  styleUrls: ['./history-info.component.scss']
})
export class HistoryInfoComponent implements OnInit {
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
