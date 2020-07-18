import { Component, OnInit, SimpleChanges } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ModalService, SelectConfig, DatatableConfig } from 'ui-master';
import { NGXLogger } from 'ngx-logger';
import { IdmReferenceService, UserType, UserGroup } from 'bff-api';
import { map } from 'rxjs/operators';
import { Router } from '@angular/router';


@Component({
  selector: 'ui-user-group',
  templateUrl: './user-group.component.html'
})
export class UserGroupComponent implements OnInit {

  userGroupFrom: FormGroup;
  submitted: boolean = false;
  reset: boolean;
  loading: boolean = false;
  button: string = 'Create';
  userGroupDT: UserGroup[] = new Array();
  uGroupList: UserGroup[] = new Array();
  result: any;
  selUserTypeCodeInit: SelectConfig;
  noDataConfig: DatatableConfig;
  userType: UserType;
  userTypeCodeDropDown = false;
  selectedCode: any;

  constructor(
    private formBuilder: FormBuilder,
    private idmRefService: IdmReferenceService,
    private modalService: ModalService,
    private router: Router,
    private logger: NGXLogger) {
    this.userGroupFrom = this.formBuilder.group({
      userGroupCode: [null],
      userGroupDesc: [null],
      userTypeCode: [null]
    });
  }

  get f() { return this.userGroupFrom.controls; }

  onSubmit() {
    this.submitted = true;
    this.loading = true;
    this.reset = false;

    this.uGroupList.length = 0;
    this.noDataConfig.redraw();

    const uGroup = new UserGroup();
    const uType = new UserType({ userTypeCode: this.f.userTypeCode.value });
    uGroup.userGroupCode = this.f.userGroupCode.value;
    uGroup.userGroupDesc = this.f.userGroupDesc.value;
    uGroup.userType = uType;

    this.idmRefService.searchUserGrp(uGroup)
      .subscribe(data => {
        if (data) {
          // reassign value - maintain roleList reference
          data.forEach(element => {
            this.uGroupList.push(element);
          });
        }
        this.noDataConfig.redraw();
      });
    // Triggers the datatable search
    this.noDataConfig.refresh();
  }

  ngOnInit() {
    this.idmRefService.findAllUserGroupList()
      .subscribe(
        data => {
          this.userGroupDT = data;
          if (data) {
            // reassign value - maintain roleList reference
            data.forEach(element => {
              this.uGroupList.push(element);
            });
          }
          this.noDataConfig.redraw();
        });
    this.selUserTypeCodeInitialization();

    this.tableInitialization();
  }

  private tableInitialization() {
    this.noDataConfig = {
      columns: [
        { field: 'SlNo', headerText: 'No.', width: '10%' },
        { field: 'userGroupCode', headerText: 'User Group Code', width: '30%' },
        { field: 'userGroupDesc', headerText: 'User Group Description' },
        { field: 'userType.userTypeCode', headerText: 'User Type Code' },
        {
          field: '', headerText: 'Action', customAttributes: {
            'no-row-click': true
          },
          columnTemplate: {
            id: 'actionDelete',
          },
          render: (data, cell, row) => {
            this.noDataConfig.columns[4].columnTemplate.context = {
              row
            };
          }
        }
      ]
    };
  }

  // tslint:disable-next-line: use-life-cycle-interface
  ngOnChanges(changes: SimpleChanges) {
    this.logger.debug(changes);
    if (changes.userGroupDT) {
      this.noDataConfig.redraw();
    }
  }

  private selUserTypeCodeInitialization() {
    this.selUserTypeCodeInit = {
      bindLabel: 'userTypeCode',
      bindValue: 'userTypeCode',
      searchable: true,
      data: () => {
        return this.idmRefService.retrieveUserTypes().pipe(map((res: UserType[]) => {
          this.userTypeCodeDropDown = res.constructor.length > 0;
          return res;
        }));
      }
    };
  }

  // reset the form data
  onReset() {
    this.reset = true;
    this.submitted = false;
    this.noDataConfig.redraw();
    this.userGroupFrom.reset();
  }

  cancel() {
    this.router.navigate(['maintenance/userGroup']);
  }

  delete(selectedRow: any) {
    this.modalService.confirm('Do you want to delete the User Group: ' + selectedRow.userGroupCode + '?').then(confirm => {
      if (confirm) {
        this.idmRefService.deleteUserGrp(selectedRow).subscribe(res => {
          if (res) {
            this.logger.debug('Deleting ', selectedRow);
            this.userGroupDT.splice(this.userGroupDT.indexOf(selectedRow), 1);
            this.noDataConfig.redraw();
          }
        });
      }
    });
  }

  rowSelected($event) {
    this.selectedCode = $event;
    this.router.navigate(['maintenance/userGroup/update', this.selectedCode.userGroupCode]);
  }

}
