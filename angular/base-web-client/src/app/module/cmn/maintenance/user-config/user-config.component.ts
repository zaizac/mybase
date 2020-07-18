import { Component, OnInit } from '@angular/core';
import { IdmConfigDto, IdmReferenceService } from 'bff-api';
import { DatatableConfig } from 'ui-master';
import { Router } from '@angular/router';

@Component({
  selector: 'ui-user-config',
  templateUrl: './user-config.component.html'
})
export class UserConfigComponent implements OnInit {

  noDataConfig: DatatableConfig;
  userCfgDt: IdmConfigDto[];
  selectedCode: any;

  constructor(
    private idmRefService: IdmReferenceService,
    private router: Router) 
    { 
      this.noDataConfig = {
        columns: [
          { field: 'SlNo', headerText: 'No.', width: '10%' },
          { field: 'configCode', headerText: 'Configuration Code', width: '25%' },
          { field: 'configDesc', headerText: 'Configuration Description' },
          { field: 'configVal', headerText: 'Configuration Value' }
        ]
      };
    }

  ngOnInit() {
    this.idmRefService.findAllUserConfig()
    .subscribe(
      data => { 
        this.userCfgDt = data;      
      })
  }

    // Datatable row selected event
    rowSelected($event) {
      this.selectedCode = $event;
      this.router.navigate(['maintenance/userConfig/', this.selectedCode.configCode]);
    }

}
