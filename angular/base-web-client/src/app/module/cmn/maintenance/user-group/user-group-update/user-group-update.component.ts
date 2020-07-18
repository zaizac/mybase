import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { UserGroup, IdmReferenceService, UserType, UserGroupBranch } from 'bff-api';
import { ModalService, SelectConfig } from 'ui-master';
import { NGXLogger } from 'ngx-logger';
import { Router, ActivatedRoute } from '@angular/router';
import { map } from 'rxjs/operators';

@Component({
  selector: 'ui-user-group-update',
  templateUrl: './user-group-update.component.html'
})
export class UserGroupUpdateComponent implements OnInit {

  userGroupUpdateFrom: FormGroup;
  submitted: boolean = false;
  reset: boolean;
  loading: boolean = false;
  button: string = "Update";
  groupCode: string;
  userGroup: UserGroup;
  selUserTypeCodeInit: SelectConfig;
  userTypeCodeDropDown = false;
  userType: UserType;
  result: any;

  constructor(
    private formBuilder: FormBuilder,
    private idmRefService: IdmReferenceService,
    private modalService: ModalService,
    private logger: NGXLogger,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.userGroupUpdateFrom = this.formBuilder.group({
      userGroupCode: [null, [Validators.required]],
      userGroupDesc: [null, [Validators.required]],
      userTypeCode: [null, [Validators.required]]
    });
   }

   get f() { return this.userGroupUpdateFrom.controls; }

  ngOnInit() {
    this.groupCode = this.route.snapshot.params.cd;
    this.getGroupCode();
    this.selUserTypeCodeInitialization();
  }

  onSubmit() {
    this.submitted = true;
    this.loading = true;
    this.reset = false;

    const uGroup = new UserGroup();
    const uType = new UserType({
      userTypeCode: this.f.userTypeCode.value
    });
    uGroup.userGroupCode = this.f.userGroupCode.value;
    uGroup.userGroupDesc = this.f.userGroupDesc.value;
    uGroup.userType = uType;
    this.idmRefService.updateUserGrp(uGroup).subscribe(
      data => {
        if (data){
          this.modalService.success("User Group Updated");
          this.router.navigate(['maintenance/userGroup']);
        }
      },
      error => {
        this.logger.error(error.error.message);
        this.modalService.error("ERROR.");
      });
  }
  
  onReset() {
  }

  private getGroupCode() {
    this.idmRefService.findGroupCodeByCode(this.groupCode).subscribe(
      data => {
        this.userGroup = data;
        this.f.userGroupCode.setValue(this.userGroup.userGroupCode);
        this.f.userGroupDesc.setValue(this.userGroup.userGroupDesc);
        this.f.userTypeCode.setValue(this.userGroup.userType.userTypeCode);
      },
      error => {
        this.logger.error(error);
      }
    );
  }

  cancel() {
    this.router.navigate(['maintenance/userGroup']);
  }

  private selUserTypeCodeInitialization() {
    this.selUserTypeCodeInit = {
      bindLabel: "userTypeCode",
      bindValue: "userTypeCode",
      searchable: true,
      data: () => {
        this.result = this.idmRefService.findAllUserTypeList().pipe(map((res: UserType[]) => {
          this.userTypeCodeDropDown = res.constructor.length > 0;
          return res;
        }))
        return this.result;
      }
    }
  }

}
