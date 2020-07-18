import { Component, OnInit, Inject, LOCALE_ID } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { SelectConfig, ModalService } from 'ui-master';
import { UserRole, IdmReferenceService, PortalType, ReferenceService, LangDesc } from 'bff-api';
import { NGXLogger } from 'ngx-logger';
import { map } from 'rxjs/operators';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'ui-mtnc-role-update',
  templateUrl: './mtnc-role-update.component.html'
})
export class MtncRoleUpdateComponent implements OnInit {

  mtncRoleForm: FormGroup;
  selPortalTypeCodeInit: SelectConfig;
  submitted = false;
  reset: boolean;
  loading = false;
  portalTypeCodeDropDown = false;
  roleCode: string;

  constructor(private idmReferenceService: IdmReferenceService,
              private referenceService: ReferenceService,
              private route: ActivatedRoute,
              private logger: NGXLogger,
              public modalSvc: ModalService,
              @Inject(LOCALE_ID) public locale: string) { }

  ngOnInit() {
    this.mtncRoleForm = new FormGroup({
       roleCode: new FormControl(null, [Validators.required]),
       roleDesc: new FormControl(null, [Validators.required]),
       portalTypeCode: new FormControl(null, [Validators.required])
    });

    // initialise drop-down select
    this.selPortalTypeCodeInitialization();

    // Getting branchId from route params
    this.route.params.subscribe((params) => {
      this.roleCode = params.id;

      // retrieve from table
      if (this.roleCode) {
        const roleToSearch = new UserRole({
          roleCode: this.roleCode
        });

        this.getRole(roleToSearch);
      }
    });
  }

  get f() { return this.mtncRoleForm.controls; }

  getRole(roleToSearch: UserRole) {
    // Triggers the datatable search
    this.idmReferenceService.searchUserRole(roleToSearch)
    .subscribe(data => {
      if (data.length > 0) {
        data.forEach(role => {
          this.f.roleCode.setValue(role.roleCode);
          this.f.roleDesc.setValue(role.roleDesc.en);
          this.f.portalTypeCode.setValue(role.portalType.portalTypeCode);
        });
      }
    });
  }


  // Form Create
  onSubmit() {
    this.submitted = true;
    this.loading = true;
    this.reset = false;

    if (this.mtncRoleForm.invalid) {
      return;
    }

    const roleToUpdate = new UserRole({
      roleCode: this.mtncRoleForm.value.roleCode as string,
      roleDesc: new LangDesc({en: this.mtncRoleForm.value.roleDesc as string}),
      portalType: new PortalType ({portalTypeCode: this.mtncRoleForm.value.portalTypeCode as string})
    });

    // update db
    this.idmReferenceService.updateUserRole(roleToUpdate).subscribe(data => {
      if (data) {
        this.modalSvc.success('Update success : ' + roleToUpdate.roleCode);
        this.onReset();
      } else {
        this.modalSvc.error('Update fail : ' + roleToUpdate.roleCode);
        this.onReset();
      }
    });
  }

  // reset the form data
  onReset() {
    this.reset = true;
    this.submitted = false;
    this.mtncRoleForm.reset();
    this.loading = false;
  }

  private selPortalTypeCodeInitialization() {
    this.selPortalTypeCodeInit = {
      bindLabel: 'portalTypeCode',
      bindValue: 'portalTypeCode',
      searchable: true,
      data: () => {
        return this.referenceService.retrievePortalTypeAll().pipe(map((res: PortalType[]) => {
          this.portalTypeCodeDropDown = res.constructor.length > 0;
          return res;
        }));
      }
    };
  }
}
