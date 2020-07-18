import { Component, OnInit, Inject, LOCALE_ID } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { SelectConfig, ModalService } from 'ui-master';
import { UserRole, IdmReferenceService, PortalType, ReferenceService, LangDesc } from 'bff-api';
import { NGXLogger } from 'ngx-logger';
import { map } from 'rxjs/operators';

@Component({
  selector: 'ui-mtnc-role-create',
  templateUrl: './mtnc-role-create.component.html'
})
export class MtncRoleCreateComponent implements OnInit {

  mtncRoleForm: FormGroup;
  selPortalTypeCodeInit: SelectConfig;
  submitted = false;
  reset: boolean;
  loading = false;
  portalTypeCodeDropDown = false;

  constructor(private idmReferenceService: IdmReferenceService,
              private referenceService: ReferenceService,
              private logger: NGXLogger,
              public modalSvc: ModalService,
              @Inject(LOCALE_ID) public locale: string) { }

  ngOnInit() {
    this.mtncRoleForm = new FormGroup({
       roleCode: new FormControl(null, [Validators.required]),
       roleDesc: new FormControl(null, [Validators.required]),
       portalTypeCode: new FormControl(null, [Validators.required])
    });

    this.selPortalTypeCodeInitialization();
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
    this.idmReferenceService.createUserRole(roleToUpdate).subscribe(data => {
      if (data) {
        this.modalSvc.success('Create success : ' + roleToUpdate.roleCode);
        this.onReset();
      } else {
        this.modalSvc.error('Create fail : ' + roleToUpdate.roleCode);
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
