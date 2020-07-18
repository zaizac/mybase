import { Component, OnInit, SimpleChanges } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ModalService, SelectConfig, DatatableConfig } from 'ui-master';
import { NGXLogger } from 'ngx-logger';
import { IdmReferenceService, UserType, UserGroup } from 'bff-api';
import { map } from 'rxjs/operators';
import { Router } from '@angular/router';

@Component({
  selector: 'ui-user-group-create',
  templateUrl: './user-group-create.component.html',
  styleUrls: []
})
export class UserGroupCreateComponent implements OnInit {

  userGroupFrom: FormGroup;
  submitted = false;
  reset: boolean;
  loading = false;
  button = 'Create';
  userGroupDT: UserGroup[];
  result: any;
  selUserTypeCodeInit: SelectConfig;
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
      userGroupCode: [null, [Validators.required]],
      userGroupDesc: [null, [Validators.required]],
      userTypeCode: [null, [Validators.required]]
    });
  }

  get f() { return this.userGroupFrom.controls; }

  onSubmit() {
    this.submitted = true;
    this.loading = true;
    this.reset = false;

    const uGroup = new UserGroup();
    const uType = new UserType({ userTypeCode: this.f.userTypeCode.value });
    uGroup.userGroupCode = this.f.userGroupCode.value;
    uGroup.userGroupDesc = this.f.userGroupDesc.value;
    uGroup.userType = uType;
    
    this.idmRefService.createUserGrp(uGroup).subscribe(
      data => {
        if (data) {
          this.modalService.success('User Group Created');
          this.router.navigate(['maintenance/userGroup']);
          //this.onReset();
        } else {
          this.userGroupDT.splice(this.userGroupDT.indexOf({
            userGroupCode: this.f.userGroupCode.value,
            userGroupDesc: this.f.userGroupDesc.value
          }), 1);
        }
      },
      error => {
        this.logger.error(error.error.message);
        this.modalService.error('ERROR.');
      });
  }

  ngOnInit() {
    this.selUserTypeCodeInitialization();
  }

  // tslint:disable-next-line: use-life-cycle-interface
  ngOnChanges(changes: SimpleChanges) {
    this.logger.debug(changes);
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
    this.userGroupFrom.reset();
  }

  cancel() {
    this.router.navigate(['maintenance/userGroup']);
  }

}
