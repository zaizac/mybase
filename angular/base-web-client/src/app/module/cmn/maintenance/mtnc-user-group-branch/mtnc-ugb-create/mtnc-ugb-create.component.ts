import { Component, OnInit, Inject, LOCALE_ID } from '@angular/core';
import { IdmReferenceService, UserGroupBranch, UserType, ReferenceService, City, State, Country } from 'bff-api';
import { NGXLogger } from 'ngx-logger';
import { ModalService, SelectConfig } from 'ui-master';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { map } from 'rxjs/operators';

@Component({
  selector: 'ui-mtnc-ugb-create',
  templateUrl: './mtnc-ugb-create.component.html'
})
export class MtncUgbCreateComponent implements OnInit {

  mtncUGBForm: FormGroup;
  selUserTypeCodeInit: SelectConfig;
  selCityCdInit: SelectConfig;
  selStateCdInit: SelectConfig;
  selCntryCdInit: SelectConfig;
  submitted = false;
  reset: boolean;
  loading = false;
  button = 'Create';
  userTypeCodeDropDown = false;
  cityCdDropDown = false;
  stateCdDropDown = false;
  cntryCdDropDown = false;
  selectedCountryISO: string;

  constructor(private idmReferenceService: IdmReferenceService,
              private referenceService: ReferenceService,
              private logger: NGXLogger,
              public modalSvc: ModalService,
              @Inject(LOCALE_ID) public locale: string) { }

  ngOnInit() {

    this.mtncUGBForm = new FormGroup({
      branchId: new FormControl(0),
      userTypeCode: new FormControl(null, [Validators.required]),
      userGroupCode: new FormControl(null, [Validators.required]),
      branchCode: new FormControl(null, [Validators.required]),
      branchName: new FormControl(null, [Validators.required]),
      branchDept: new FormControl(),
      addr1: new FormControl(null, [Validators.required]),
      addr2: new FormControl(),
      addr3: new FormControl(),
      addr4: new FormControl(),
      addr5: new FormControl(),
      cityCd: new FormControl(null, [Validators.required]),
      stateCd: new FormControl(null, [Validators.required]),
      cntryCd: new FormControl(null, [Validators.required]),
      zipcode: new FormControl(),
      email: new FormControl(),
      faxNo: new FormControl(),
      contactNo: new FormControl(),
      status: new FormControl()
    });

    // initialise drop-down select
    this.selUserTypeCodeInitialization();
    this.selCityCodeInitialization();
    this.selStateCodeInitialization();
    this.selCountryCodeInitialization();
  }

  // Form Create
  onSubmit() {
    this.submitted = true;
    this.loading = true;
    this.reset = false;

    if (this.mtncUGBForm.invalid) {
      return;
    }

    const ugbToUpdate = new UserGroupBranch({
      branchId: this.mtncUGBForm.value.branchId,
      userType: new UserType({ userTypeCode: this.mtncUGBForm.value.userTypeCode as string }),
      userGroupCode: this.mtncUGBForm.value.userGroupCode,
      branchCode: this.mtncUGBForm.value.branchCode,
      branchName: this.mtncUGBForm.value.branchName,
      branchDept: this.mtncUGBForm.value.branchDept,
      addr1: this.mtncUGBForm.value.addr1,
      addr2: this.mtncUGBForm.value.addr2,
      addr3: this.mtncUGBForm.value.addr3,
      addr4: this.mtncUGBForm.value.addr4,
      addr5: this.mtncUGBForm.value.addr5,
      cityCd: this.mtncUGBForm.value.cityCd,
      stateCd: this.mtncUGBForm.value.stateCd,
      cntryCd: this.mtncUGBForm.value.cntryCd,
      zipcode: this.mtncUGBForm.value.zipcode,
      email: this.mtncUGBForm.value.email,
      faxNo: this.mtncUGBForm.value.faxNo,
      contactNo: this.mtncUGBForm.value.contactNo,
      status: this.mtncUGBForm.value.status
    });

    // update db
    this.idmReferenceService.createUserGroupBranch(ugbToUpdate).subscribe(data => {
      if (data) {
        this.modalSvc.success('Create success : ' + ugbToUpdate.branchCode);
        this.onReset();
      } else {
        this.modalSvc.error('Create fail : ' + ugbToUpdate.branchCode);
        this.onReset();
      }
    });
  }

  // reset the form data
  onReset() {
    this.reset = true;
    this.submitted = false;
    this.mtncUGBForm.reset();
  }

  private selUserTypeCodeInitialization() {
    this.selUserTypeCodeInit = {
      bindLabel: 'userTypeCode',
      bindValue: 'userTypeCode',
      searchable: true,
      data: () => {
        return this.idmReferenceService.retrieveUserTypes().pipe(map((res: UserType[]) => {
          this.userTypeCodeDropDown = res.constructor.length > 0;
          return res;
        }));
      }
    };
  }

  private selCityCodeInitialization() {
    this.selCityCdInit = {
      bindLabel: 'cityCd',
      bindValue: 'cityCd',
      searchable: true,
      data: () => {
        return this.referenceService.retrieveCities().pipe(map((res: City[]) => {
          this.cityCdDropDown = res.constructor.length > 0;
          return res;
        }));
      }
    };
  }

  private selStateCodeInitialization() {
    this.selStateCdInit = {
      bindLabel: 'stateCd',
      bindValue: 'stateCd',
      searchable: true,
      data: () => {
        return this.referenceService.retrieveStates().pipe(map((res: State[]) => {
          this.stateCdDropDown = res.constructor.length > 0;
          return res;
        }));
      }
    };
  }

  private selCountryCodeInitialization() {
    this.selCntryCdInit = {
      bindLabel: 'cntryCd',
      bindValue: 'cntryCd',
      searchable: true,
      data: () => {
        return this.referenceService.retrieveCountries().pipe(map((res: Country[]) => {
          this.cntryCdDropDown = res.constructor.length > 0;
          return res;
        }));
      }
    };
  }

}
