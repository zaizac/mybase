import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { DatatableConfig } from 'projects/ui-master/src/lib/component/datatable/datatable.config';
import { mockServerSidePaginationResponse } from '../mockData';

@Component({
  selector: 'app-dt-search-reset',
  templateUrl: './dt-search-reset.component.html',
  styleUrls: ['./dt-search-reset.component.scss']
})
export class DtSearchResetComponent implements OnInit {
  headerConfig: DatatableConfig;
  testForm: FormGroup;

  constructor(private formBuilder: FormBuilder) {
    this.testForm = this.formBuilder.group({
      name: null
    });
  }

  get f() {
    return this.testForm.controls;
  }

  ngOnInit() {
    this.headerConfig = {
      columns: [{ field: 'SlNo' }, { field: 'name' }, { field: 'gender' }, { field: 'company' }],
      searchForm: this.f,
      data: request => {
        return mockServerSidePaginationResponse(request);
      }
    };
  }

  onSubmit() {
    // Triggers the datatable search
    this.headerConfig.refresh();
  }

  reset() {
    this.testForm.reset();
    // Triggers the datatable reset
    this.headerConfig.reload();
  }
}
