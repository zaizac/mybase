import { Component, OnInit } from '@angular/core';
import { DatatableConfig } from 'projects/ui-master/src/lib/component/datatable/datatable.config';
import { mockServerSidePaginationResponse } from '../mockData';

@Component({
  selector: 'app-dt-expandablerow',
  templateUrl: './dt-expandablerow.component.html',
  styleUrls: ['./dt-expandablerow.component.scss']
})
export class DtExpandablerowComponent implements OnInit {
  headerConfig: DatatableConfig;
  noData = [];
  constructor() {}

  ngOnInit() {
    this.headerConfig = {
      columns: [
        {
          type: 'expand',
          columnTemplate: {
            id: 'expandTemplate'
          },
          render: (data, cell, row, config) => {
            const context = {
              name: row.name,
              gender: row.gender,
              company: row.company
            };
            this.headerConfig.columns[0].columnTemplate.context = context;
            config.isDisabled = row.gender === 'male';
            config.isExpand = row.company === 'Velity';
          }
        },
        { field: 'SlNo' },
        { field: 'name' },
        { field: 'gender' },
        { field: 'company' }
      ],
      selectedData: [],
      data: request => {
        return mockServerSidePaginationResponse(request);
      }
    };
  }
}
