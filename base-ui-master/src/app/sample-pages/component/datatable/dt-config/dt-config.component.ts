import { Component, OnInit } from '@angular/core';
import { DatatableConfig } from 'projects/ui-master/src/lib/component/datatable/datatable.config';
import { DTDATA, SIMPLEDATA } from '../mockData';

@Component({
  selector: 'app-dt-config',
  templateUrl: './dt-config.component.html',
  styleUrls: ['./dt-config.component.scss']
})
export class DtConfigComponent implements OnInit {
  noData = [];
  dtData: any = DTDATA;
  dtData1 = SIMPLEDATA;
  headerConfig: DatatableConfig;
  headerConfig2: DatatableConfig;
  headerConfig3: DatatableConfig;
  headerConfig4: DatatableConfig;
  headerConfig5: DatatableConfig;

  constructor() {}

  ngOnInit() {
    this.headerConfig = {
      columns: [
        { field: 'name', headerText: 'Name', width: '30%' },
        { field: 'SlNo', headerText: 'No.', width: '10%' },
        { field: 'gender' },
        { field: 'age', headerText: 'Age' }
      ]
    };

    this.headerConfig2 = {
      columns: [{ field: 'SlNo' }, { field: 'name' }, { field: 'gender' }, { field: 'company' }],
      initialPage: {
        pageSize: 7,
        pageSizes: false,
        pageCount: 8
      }
    };

    this.headerConfig3 = {
      columns: [{ field: 'SlNo' }, { field: 'name' }, { field: 'gender' }, { field: 'company' }],
      initialPage: {
        pageSizes: [3, 6, 9, 12, 15]
      }
    };

    this.headerConfig4 = {
      columns: [{ field: 'name', headerText: 'Name', width: '30%' }],
      displayText: {
        emptyRecord: 'This is empty record display',
        pagerDropDown: '#pager records in one page',
        currentPageInfo: 'Display {0} of {1}',
        totalItemsInfo: '(Total {0} rows in all pages)'
      }
    };

    this.headerConfig5 = {
      columns: [
        { field: 'SlNo', headerText: 'No.', width: '20%' },
        { field: 'name', headerText: 'Name', width: '30%' },
        { field: 'age', headerText: 'Age', width: '20%' },
        { field: 'gender', headerText: 'Gender', width: '20%' },
        { field: 'company', headerText: 'Company', width: '20%' }
      ],
      textWrap: {
        allowWrap: true,
        mode: 'Both'
      }
    };
  }
}
