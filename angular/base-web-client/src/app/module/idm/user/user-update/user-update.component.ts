
import { Component, OnInit, AfterContentInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormArray } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { NGXLogger } from 'ngx-logger';
import { of } from 'rxjs';

import { AuthService, IdmRoleConstants, UserService, ReferenceService, UserProfile, Documents, StaticData } from 'bff-api';
import { ModalService, SelectConfig } from 'ui-master';
import { map } from 'rxjs/operators';
import { UserRoleGroup } from 'bff-api/lib/models/userRoleGroup';

@Component({
  selector: 'ui-user-update',
  templateUrl: './user-update.component.html'
})
export class UserUpdateComponent implements OnInit, AfterContentInit {

  // Datepicker config
  minDate: Date = new Date('1921-01-01');
  maxDate: Date = new Date();

  userForm: FormGroup;
  submitted: boolean = false;
  reset: boolean = false;
  loading: boolean = false;

  currentUser = this.authService.currentUserValue;
  currProfile: UserProfile;
  userId: string;
  responsemsg: any;
  isPortalAdmin: boolean = false;
  userRoleGroupCodeDropDown = false;
  profilePage: boolean = false;
  selUserRoleGroupInit: SelectConfig;
  selUserRoleGroupCntryInit: SelectConfig;
  selUserRoleGroupBrnchInit: SelectConfig;
  userStatusList: Map<string, string>;
  status: string;
  refDocLst: Documents[];
  isViewOnly: boolean = false;
  showCountrySel: boolean = false;
  showBranchSel: boolean = false;
  defaultCntryCd: string;

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private userService: UserService,
    private reference: ReferenceService,
    private route: ActivatedRoute,
    private modalService: ModalService,
    private logger: NGXLogger
  ) {
    // User Form Builder
    this.userForm = this.formBuilder.group({
      userId: [{ value: '', disable: true }, [Validators.required]],
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
      status: null,
      statusDesc: [{ value: '', disable: true }],
      fileUploads: this.formBuilder.array([]),
      authOption: null,
    });
  }

  // Get all fileUploads controls as form array.
  get fileUploads(): FormArray {
    return this.userForm.get('fileUploads') as FormArray;
  };

  ngOnInit() {
    // Set DOB minus 18 years from current date
    this.maxDate.setFullYear(this.maxDate.getFullYear() - 18);

    this.userRoleGroupCodeDropDown = this.authService.hasAnyRole(IdmRoleConstants.SYSTEM_ADMIN, IdmRoleConstants.DQ_ADMIN, IdmRoleConstants.JIM_ADMIN, IdmRoleConstants.OSC_ADMIN, IdmRoleConstants.RA_ADMIN);
    if (this.route.snapshot.data.type === 'PROFILE') {
      const currentUser = this.authService.currentUserValue;
      this.userId = currentUser.userId;
      this.profilePage = true;
    } else {
      // Getting userId from route params
      this.route.params.subscribe((params) => {
        this.userId = params.id;
      });
      this.profilePage = false;
    }

    // Retrieve User Status List
    this.getUserStatus();

    // Select Initialization - User Role Group  
    this.selUserRoleGroupInitialization();

    // Get reference document for Profile Picture    
    this.reference.findDocumentsByTrxnNo('PROFPIC').subscribe(
      data => {
        this.refDocLst = data;
      },
      error => {
        this.logger.error(error);
        this.refDocLst = [];
      });

    // Assign initial form value
    this.getProfile();
  }

  ngAfterContentInit(): void { }

  get f() { return this.userForm.controls; }

  onSubmit() {
    this.submitted = true;
    this.loading = true;
    this.reset = false;

    if (this.userForm.invalid) {
      return;
    }

    const currentUser = this.authService.currentUserValue;
    this.isPortalAdmin = this.authService.hasAnyRole(IdmRoleConstants.SYSTEM_ADMIN, IdmRoleConstants.DQ_ADMIN, IdmRoleConstants.JIM_ADMIN, IdmRoleConstants.OSC_ADMIN, IdmRoleConstants.RA_ADMIN);

    const isProfile: boolean = this.route.snapshot.data.type === 'PROFILE';
    const user = new UserProfile;

    if (isProfile) {
      user.userType = currentUser.userType;
    }

    user.userId = this.f.userId.value;
    user.firstName = this.f.firstName.value;
    user.lastName = this.f.lastName.value;
    user.nationalId = this.f.nationalId.value;
    user.email = this.f.email.value;
    user.dob = this.f.dob.value;
    user.gender = this.f.gender.value;
    user.contactNo = this.f.phone.value;
    user.position = this.f.position.value;
    
    // Authorization Option only for Logged In Profile update
    if(this.profilePage) {
      user.authOption = this.f.authOption.value;
    }

    if (this.f.fileUploads.value) {
      user.fileUploads = this.f.fileUploads.value[0];
    }
    user.cntryCd = this.f.userGroupCntry.value;
    user.userGroupRoleBranchCode = this.f.userGroupBrnch.value;

    user.userRoleGroupCode = this.f.userGroup.value;
    var userGroup = this.f.userGroup.value;
    if (this.isPortalAdmin && userGroup == null) {

      user.userRoleGroupCode = currentUser.userRoleGroupCode;
    } else {
      user.userRoleGroupCode = userGroup;
    }

    this.userService.updateUser(user, isProfile)
      .subscribe(
        data => {
          this.modalService.success(data.toString());
          this.onReset();
        },
        error => {
          this.logger.error(error);
          this.modalService.error(error.error.errorMessage);
        });
  }

  getProfile() {
    this.userService.getByUserId(this.userId)
      .subscribe(
        data => {
          this.currProfile = data;
          this.status = this.currProfile.status;
          this.f.statusDesc.setValue(this.userStatusList.get(this.status));
          this.f.status.setValue(this.status);
          this.f.firstName.setValue(this.currProfile.firstName);
          this.f.lastName.setValue(this.currProfile.lastName);
          this.f.nationalId.setValue(this.currProfile.nationalId);
          this.f.email.setValue(this.currProfile.email);
          this.f.dob.setValue((this.currProfile.dob));
          this.f.gender.setValue(this.currProfile.gender);
          this.f.phone.setValue(this.currProfile.contactNo);
          this.f.position.setValue(this.currProfile.position);
          this.f.userId.setValue(this.currProfile.userId);
          this.f.userGroup.setValue(this.currProfile.userRoleGroup.userGroupCode);
          this.f.userGroupCntry.setValue(this.currProfile.cntryCd);

          // Authorization Option only for Logged In Profile update
          if(this.profilePage) {
            this.f.authOption.setValue(this.currProfile.authOption);
          }
          
          if (this.currProfile.cntryCd) {
            this.showCountrySel = true;
          }
          this.f.userGroupBrnch.setValue(this.currProfile.userGroupRoleBranchCode);
          if (this.currProfile.fileUploads != null) {
            this.userForm.setControl('fileUploads', this.formBuilder.array(this.currProfile.fileUploads || []));
          }
          this.enableDisableForm();
          this.showExtraDropdown();
        },
        error => {
          this.logger.error(error);
        });
  }

  // Other user profile actions
  userAction(status) {
    this.userService.userAction(this.userId, status)
      .subscribe(
        data => {
          if (data) {
            if (status == 'A' && status != 'F') {
              this.status = 'I';
            } else if (status == 'I' && status != 'F') {
              this.status = 'A';
            } else {
              this.status = 'F';
            }
            this.enableDisableForm();
            this.modalService.success(data.toString());
          }
        },
        error => {
          this.logger.error(error);
          this.modalService.error(error.error.errorMessage);
        });
  }

  test(e) {

  }

  // reset the form data
  onReset() {
    this.reset = true;
    this.submitted = false;
    this.showCountrySel = false;
    this.showBranchSel = false;
    this.getProfile();
  }

  private enableDisableForm() {
    if (this.status === 'I') {
      this.userForm.disable();
      this.isViewOnly = true;
    } else {
      this.userForm.enable();
      this.f.statusDesc.disable();
      this.f.userId.disable();
      this.isViewOnly = false;
    }
  }

  private selUserRoleGroupInitialization() {
    this.selUserRoleGroupInit = {
      bindLabel: "userGroupDesc",
      bindValue: "userGroupCode",
      searchable: true,
      data: () => {
        if (this.userRoleGroupCodeDropDown) {
          let result = this.userService
            .getUserRoleGroups(
              this.currentUser.userType.userTypeCode,
              this.currentUser.userRoleGroup.userGroupCode,
              true, true
            ).pipe(map((res: UserRoleGroup[]) => {
              this.userRoleGroupCodeDropDown = res.constructor.length > 0;

              return res;
            }))
          return result;
        }
        return of([]);
      }
    }
  }

  onChangeUserRoleGroup(e: any) {
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
        this.selUserGroupBranchInit();
        this.f.userGroupBrnch.setValidators([Validators.required]);
      }
    }
  }


  private showExtraDropdown() {
    if (this.showCountrySel) {
      this.selUserGroupCountryInitialization();
    }

    if (this.showBranchSel) {
      this.selUserGroupBranchInit();
    }
  }

  // User Status Static List
  private getUserStatus() {
    this.reference.userStatusMap().subscribe(
      data => {
        this.userStatusList = data;
      },
      error => { this.logger.error(error); }
    );
  }

  private selUserGroupCountryInitialization() {
    this.selUserRoleGroupCntryInit = {
      bindLabel: "cntryDesc",
      bindValue: "cntryCd",
      searchable: true,
      data: (e) => {
        if (this.userRoleGroupCodeDropDown) {
          return this.reference.countryServe()
        }
        return of([]);
      }
    }
  }

  private selUserGroupBranchInit() {
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
