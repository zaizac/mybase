import { Component, OnInit } from '@angular/core';
import { DatatableConfig } from 'projects/ui-master/src/lib/component/datatable/datatable.config';
import { mockServerSidePaginationResponse, SIMPLEDATA } from '../mockData';

@Component({
  selector: 'app-dt-render',
  templateUrl: './dt-render.component.html',
  styleUrls: ['./dt-render.component.scss']
})
export class DtRenderComponent implements OnInit {
  headerConfig: DatatableConfig;
  headerConfig2: DatatableConfig;

  dtData = SIMPLEDATA;

  constructor() {}

  ngOnInit() {
    this.headerConfig = {
      columns: [
        { field: 'name', headerText: 'Name', width: '30%' },
        { field: 'SlNo', headerText: 'No.', width: '10%' },
        {
          field: 'gender',
          headerText: 'Gender',
          width: '20%',
          render: (data, cell, row) => {
            if (row.age >= 20) {
              cell.textContent = 'Adult ' + data;
            } else {
              cell.textContent = data.substr(0, 1).toUpperCase() + data.substr(1).toLowerCase();
            }
          }
        },
        {
          field: 'age',
          headerText: 'Age',
          width: '10%',
          allowSorting: false,
          render: (data, cell, row) => {
            cell.textContent = data || '-';
          }
        },
        { field: 'company', headerText: 'Company', width: '30%' }
      ]
    };

    this.headerConfig2 = {
      columns: [{ field: 'SlNo', width: 'auto' }, { field: 'name' }, { field: 'gender' }, { field: 'company' }],
      selectedData: [],
      data: request => {
        return mockServerSidePaginationResponse(request);
      }
    };
  }

  checkboxClick(ev, column) {
    if (ev.target.checked) {
      if (column === 'name') {
        this.headerConfig2.columns[1].visible = true;
      }
      if (column === 'gender') {
        this.headerConfig2.columns[2].visible = true;
      }
      if (column === 'company') {
        this.headerConfig2.columns[3].visible = true;
      }
    } else {
      if (column === 'name') {
        this.headerConfig2.columns[1].visible = false;
      }
      if (column === 'gender') {
        this.headerConfig2.columns[2].visible = false;
      }
      if (column === 'company') {
        this.headerConfig2.columns[3].visible = false;
      }
    }
    this.headerConfig2.redraw();
  }
}
