import { Component, OnInit } from '@angular/core';
import { DatatableConfig } from 'projects/ui-master/src/lib/component/datatable/datatable.config';
import { mockServerSidePaginationResponse } from '../mockData';

@Component({
  selector: 'app-dt-checkbox-radio',
  templateUrl: './dt-checkbox-radio.component.html',
  styleUrls: ['./dt-checkbox-radio.component.scss']
})
export class DtCheckboxRadioComponent implements OnInit {
  headerConfig: DatatableConfig;
  headerConfig2: DatatableConfig;
  headerConfig3: DatatableConfig;

  constructor() {}

  ngOnInit() {
    this.headerConfig = {
      columns: [
        { field: 'SlNo' },
        {
          type: 'checkbox',
          render(data, cell, row, config) {
            config.isDisabled = row.gender === 'male';
          }
        },
        { field: 'name' },
        { field: 'gender' },
        { field: 'company' }
      ],
      data: request => {
        return mockServerSidePaginationResponse(request);
      },
      initDrawCallBack: data => {
        const selectedData = [data[0], data[6]];
        this.headerConfig.selectedData = selectedData;
      }
    };

    this.headerConfig2 = {
      columns: [
        {
          type: 'radio',
          render(data, cell, row, config) {
            config.isDisabled = row.gender === 'male';
          }
        },
        { field: 'SlNo' },
        { field: 'name' },
        { field: 'gender' },
        { field: 'company' }
      ],
      data: request => {
        return mockServerSidePaginationResponse(request);
      },
      initDrawCallBack: data => {
        this.headerConfig2.selectedRowData = data[2];
      }
    };
    this.headerConfig3 = {
      columns: [
        {
          type: 'checkbox'
        },
        {
          type: 'radio'
        },
        { field: 'name' },
        { field: 'gender' },
        { field: 'company' }
      ],
      data: request => {
        return mockServerSidePaginationResponse(request);
      },
      checkboxLimit: 5
    };
  }
}
