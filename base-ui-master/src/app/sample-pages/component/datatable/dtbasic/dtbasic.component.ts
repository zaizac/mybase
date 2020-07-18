import { Component, OnDestroy, OnInit, ViewContainerRef } from '@angular/core';
import { DatatableConfig } from 'projects/ui-master/src/lib/component/datatable/datatable.config';
import { DTDATA, mockServerSidePaginationResponse, SIMPLEDATA } from '../mockData';

@Component({
  selector: 'app-dtbasic',
  templateUrl: './dtbasic.component.html',
  styleUrls: ['./dtbasic.component.scss']
})
export class DtbasicComponent implements OnInit, OnDestroy {
  dtConfig: DatatableConfig;
  headerConfig: DatatableConfig;
  headerConfig11: DatatableConfig;
  noDataConfig: DatatableConfig;
  noData: any = [];
  dtData: any = DTDATA;
  dtData2 = SIMPLEDATA;

  constructor(private viewContainerRef: ViewContainerRef) {}

  public ngOnInit(): void {
    this.noDataConfig = {
      columns: [
        { field: 'name', headerText: 'Name', width: '30%' },
        { field: 'SlNo', headerText: 'No.', width: '10%' },
        { field: 'gender' },
        { field: 'age', headerText: 'Age' }
      ]
    };

    this.dtConfig = {
      data: request => {
        return mockServerSidePaginationResponse({
          start: request.start,
          length: request.length,
          draw: request.draw
        });
      }
    };
  }

  ngOnDestroy() {
    // this.appendDomService.destroyComponent();
  }
}
