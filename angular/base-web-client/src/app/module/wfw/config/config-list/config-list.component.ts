import { Component, OnInit } from '@angular/core';
import { DatatableConfig, RouterDataService } from 'ui-master';
import { WfwReferenceService } from 'bff-api';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup } from '@angular/forms';
import { queryParams } from '@syncfusion/ej2-base';

@Component({
  selector: 'ui-config-list',
  templateUrl: './config-list.component.html'
})
export class ConfigListComponent implements OnInit {

  tableInit: DatatableConfig;
  configForm: FormGroup;

  constructor(
    private wfwRefSvc: WfwReferenceService,
    private router: Router,
    private formBuilder: FormBuilder) {
      this.configForm = this.formBuilder.group({});
  }

  ngOnInit() {
    this.tableInitialization();
  }

  private tableInitialization() {
    this.tableInit = {
      columns: [
        { field: 'SlNo', headerText: 'No.', allowSorting: false, width: 6 },
        {
          field: 'description',
          headerText: 'Type',
          width: 25
        },
        {
          field: 'tranId',
          headerText: 'Transaction Id',
          width: 15,
          // render: (data, cell, row) => {
          //   cell.textContent = row.userRoleGroupDesc.toUpperCase();
          // }
        },
        {
          field: 'moduleId',
          headerText: 'Module Id',
          width: 12,
          // render: (data, cell, row) => {
          //   // cell.textContent = this.userStatusList.get(data);
          // }
        },
        {
          field: 'autoClaim',
          headerText: 'Auto Claim',
          width: 12,
          render: (data, cell, row) => {
            cell.textContent = data == 1 ? 'Yes' : 'No';
          }
        },
        {
          field: 'status',
          headerText: 'Is Active',
          width: 10,
          render: (data, cell, row) => {
            cell.textContent = data == 1 ? 'Yes' : 'No';
          }
        },
        {
          field: 'redirectPath',
          headerText: 'Redirect Path',
          width: 20,
          // render: (data, cell, row) => {
          //   // cell.textContent = this.userStatusList.get(data);
          // }
        }
      ],
      // searchForm: this.f,
      data: dtRequest => {
        return this.wfwRefSvc.getRefTypePagination(dtRequest);
      }
    };
  }

  onSubmit() { }

  rowSelected(row) {
    this.router.navigate(['workflow/configuration/type'], { state: row });
  }
  
}
