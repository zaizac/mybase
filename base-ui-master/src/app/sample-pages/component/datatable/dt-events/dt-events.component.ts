import { Component, OnInit } from '@angular/core';
import { DatatableConfig } from 'projects/ui-master/src/lib/component/datatable/datatable.config';
import { mockServerSidePaginationResponse } from '../mockData';

@Component({
  selector: 'app-dt-events',
  templateUrl: './dt-events.component.html',
  styleUrls: ['./dt-events.component.scss']
})
export class DtEventsComponent implements OnInit {
  headerConfig: DatatableConfig;

  constructor() { }

  ngOnInit() {
    this.headerConfig = {
      columns: [
        { field: 'SlNo' },
        { field: 'name' },
        { field: 'gender' },
        { field: 'company' },
        {
          type: 'checkbox'
        }
      ],
      selectedData: [],
      data: request => {
        return mockServerSidePaginationResponse(request);
      }
    };
  }

  // Datatable row selected event
  handleRowClick(row) {
    alert(`${row.name} is working in ${row.company}`);
  }
}
