import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormArray, FormControl, Validator, ValidatorFn, AbstractControl } from '@angular/forms';
import { NGXLogger } from 'ngx-logger';
import { AuthService, IdmRoleConstants, UserService, Documents, CustomMultipartFile, UserProfile, ReferenceService } from 'bff-api';
import { ModalService, SelectConfig } from 'ui-master';
import { environment } from '@environments/environment';
import { of, config } from 'rxjs';
import { map } from 'rxjs/operators';
import { UserRoleGroup } from 'bff-api/lib/models/userRoleGroup';

@Component({
  selector: 'ui-user-create',
  templateUrl: './user-create.component.html'
})
export class UserCreateComponent implements OnInit {

  // Datepicker config
  minDate: Date = new Date('1921-01-01');
  maxDate: Date = new Date();

  userForm: FormGroup;
  submitted: boolean = false;
  reset: boolean = false;
  loading: boolean = false;

  isPortalAdmin: boolean = false;
  userRoleGroupCodeDropDown = false;
  selUserRoleGroupInit: SelectConfig;
  selUserRoleGroupCntryInit: SelectConfig;
  selUserRoleGroupBrnchInit: SelectConfig;
  refDocLst: Documents[];
  defaultFileUploads: FormControl[];
  showCountrySel: boolean = false;
  showBranchSel: boolean = false;
  defaultCntryCd: string;
  defaultCountryISO: string = 'bd';

  currentUser = this.authService.currentUserValue;

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private userService: UserService,
    private modalService: ModalService,
    private reference: ReferenceService,
    private logger: NGXLogger
  ) {
    // User Form Builder
    this.userForm = this.formBuilder.group({
      firstName: [null, [Validators.required]],
      lastName: null,
      nationalId: [null, [Validators.required]],
      gender: [null, [Validators.required]],
      dob: [null, [Validators.required]],
      email: [null, [Validators.required]],
      phone: [null, [Validators.required]],
      position: null,
      userGroup: [null, [Validators.required]],
      userGroupCntry: null,
      userGroupBrnch: null,
      fileUploads: this.formBuilder.array([]),
    });
  }


  // Get all fileUploads controls as form array.
  get fileUploads(): FormArray {
    return this.userForm.get('fileUploads') as FormArray;
  };

  ngOnInit() {
    // Check logged in user if an admin
    this.isPortalAdmin = this.authService.hasAnyRole(IdmRoleConstants.SYSTEM_ADMIN, IdmRoleConstants.DQ_ADMIN, IdmRoleConstants.JIM_ADMIN, IdmRoleConstants.OSC_ADMIN, IdmRoleConstants.RA_ADMIN);

    // Set DOB minus 18 years from current date
    this.maxDate.setFullYear(this.maxDate.getFullYear() - 18);

    // Select Initialization - User Role Group
    this.selUserRoleGroupInitialization();

    // Get reference document for Profile Picture    
    this.reference.findDocumentsByTrxnNo('PROFPIC').subscribe(
      data => {
        this.refDocLst = data;
        this.setFileUploads(this.refDocLst);
      },
      error => {
        this.logger.error(error);
        this.refDocLst = [];
      });
  }

  get f() { return this.userForm.controls; }

  onSubmit() {
    this.submitted = true;
    this.loading = true;
    this.reset = false;

    if (this.userForm.invalid) {
      return;
    }

    const user = new UserProfile();
    user.firstName = this.f.firstName.value;
    user.lastName = this.f.lastName.value;
    user.nationalId = this.f.nationalId.value;
    user.email = this.f.email.value;
    user.dob = this.f.dob.value;
    user.gender = this.f.gender.value;
    user.contactNo = this.f.phone.value;
    user.position = this.f.position.value;
    if (this.f.fileUploads.value) {
      user.fileUploads = this.f.fileUploads.value[0];
    }
    user.cntryCd = this.f.userGroupCntry.value;
    user.userGroupRoleBranchCode = this.f.userGroupBrnch.value;
    
    // Assign Current User Country
    if(!this.f.userGroupCntry.value) {
      console.log('Assign cntryCd from logged in user....')
      user.cntryCd = this.currentUser.cntryCd;
    }

    // Assign Current User Branch
    if(!this.f.userGroupBrnch.value) {
      user.userGroupRoleBranchCode = this.currentUser.userGroupRoleBranchCode;
    }

    user.userRoleGroupCode = this.f.userGroup.value;
    if (this.isPortalAdmin) {
      console.log('isPortalAdmin')
      const currentUser = this.authService.currentUserValue;
      if (!user.userRoleGroupCode) {
        user.userRoleGroupCode = currentUser.userRoleGroupCode;
      }
      user.cntryCd = currentUser.cntryCd;
      user.userType = currentUser.userType;
      user.isAdmin = 'Y';
    } else {
      //TODO: user.userType = `${environment.portalType}`;
      user.isAdmin = 'N';
    }
    user.portalType = { portalTypeCode: environment.portalType, portalTypeDesc: null };

    this.userService.createUser(user)
      .subscribe(
        data => {
          if (data) {
            this.modalService.success(data.toString());
            this.onReset();
          }
        },
        error => {
          this.logger.error(error.error.message);
          this.modalService.error("Failed to process the transaction. Please contact system administrator.");
        }
      );
  }

  // reset the form data
  onReset() {
    this.reset = true;
    this.submitted = false;
    this.showCountrySel = false;
    this.showBranchSel = false;
    this.userForm.reset();
    if (this.userForm.controls.fileUploads) {
      this.userForm.controls.fileUploads.setValue(this.defaultFileUploads);
    }
  }

  // Set default values of File Upload array from Ref Documents
  private setFileUploads(refDoc: Documents[]) {
    refDoc.forEach(doc => {
      let obj = new CustomMultipartFile(JSON.parse(JSON.stringify(doc)));
      (<FormArray>this.userForm.get('fileUploads')).push(new FormControl(obj));
    });
    this.defaultFileUploads = this.userForm.controls.fileUploads.value;
  }

  private selUserRoleGroupInitialization() {
    this.selUserRoleGroupInit = {
      bindLabel: "userGroupDesc",
      bindValue: "userGroupCode",
      searchable: true,
      data: () => {
        const currentUser = this.authService.currentUserValue;
        let result = this.userService
          .getUserRoleGroups(
            currentUser.userType.userTypeCode,
            currentUser.userRoleGroup.userGroupCode,
            true, true
          ).pipe(map((res: UserRoleGroup[]) => {
            this.userRoleGroupCodeDropDown = res.constructor.length > 0;
            return res;
          }))
        return result;
      }
    }
  }

  onChangeUserRoleGroup(e) {
    if (e) {
      this.showCountrySel = e.selCountry;
      this.showBranchSel = e.selBranch;
      this.defaultCntryCd = e.cntryCd;
      this.showExtraDropdown();
    }
  }

  onChangeUserRoleCntry(e) {
    if (e) {
      if (this.showBranchSel) {
        if (this.selUserRoleGroupBrnchInit) {
          this.f.userGroupCntry.setValue(e.cntryCd);
          this.selUserRoleGroupBrnchInit.refresh();
        } else {
          this.selUserGroupBranchInit(null);
          this.f.userGroupBrnch.setValidators([Validators.required]);
        }
      }
    }
  }

  private showExtraDropdown() {
    if (this.showCountrySel) {
      this.selUserGroupCountryInitialization();
      this.f.userGroupCntry.setValidators([Validators.required]);
    }

    if (this.showBranchSel) {
      this.selUserGroupBranchInit(null);
      this.f.userGroupBrnch.setValidators([Validators.required]);
    }
  }

  private selUserGroupCountryInitialization() {
    this.selUserRoleGroupCntryInit = {
      bindLabel: "cntryDesc",
      bindValue: "cntryCd",
      searchable: true,
      data: () => {
        if (this.userRoleGroupCodeDropDown) {
          return this.reference.countryServe()
        }
        return of([]);
      }
    }
  }

  private selUserGroupBranchInit(cntry: string) {
    this.selUserRoleGroupBrnchInit = {
      bindLabel: "branchName",
      bindValue: "branchCode",
      searchable: true,
      data: () => {
        if (this.userRoleGroupCodeDropDown) {
          let cntryCd = this.f.userGroupCntry.value;
          if (!cntryCd) {
            cntryCd = this.currentUser.cntryCd;
            if (!cntryCd) {
              cntryCd = '';
            }
          }
          return this.userService.searchUserGroupBranch(this.f.userGroup.value, cntryCd);
        }
        return of([]);
      }
    }
  }

}
