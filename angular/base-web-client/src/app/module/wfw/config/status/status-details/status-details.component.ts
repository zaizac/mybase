import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { WfwReferenceService, AuthService } from 'bff-api';
import { SelectConfig, ModalService } from 'ui-master';

@Component({
  selector: 'ui-status-details',
  templateUrl: './status-details.component.html'
})
export class StatusDetailsComponent implements OnInit {

  statusDtls: any;
  statusForm: FormGroup;
  isNew: boolean = true;
  submitted: boolean = false;
  reset: boolean;
  selLevelInit: SelectConfig;
  typeId: string;
  button: string = "Create";

  constructor(
    private router: Router,
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private wfwRefSvc: WfwReferenceService,
    private authSvc: AuthService,
    private modalSvc: ModalService) {

    this.statusForm = this.formBuilder.group({
      type: [null, [Validators.required]],
      level: [null, [Validators.required]],
      statusDesc: [null, [Validators.required]],
      statusCd: [null, [Validators.required]],
      statusSequence: null,
      nextLevelId: null,
      noRelease: new FormControl(0),
      initialState: [null, [Validators.required]],
      display: [null, [Validators.required]],
      status: [null, [Validators.required]],
      color: new FormControl('#90a4ae')
    });
  }

  ngOnInit() {

    this.route.queryParams.subscribe(params => {
      this.typeId = params.typeId;
      this.wfwRefSvc.getRefConfig(params).subscribe(
        data => {
          this.statusDtls = data;
          this.setTypeDetails();
        });
    });

    this.selectLevel();
  }

  get f() { return this.statusForm.controls; }

  setTypeDetails() {
    this.f.type.disable();
    this.f.level.disable();
    this.f.type.setValue(this.statusDtls.description);
    this.f.level.setValue(this.statusDtls.refLevel.description);

    if (this.statusDtls.refStatus) {
      this.isNew = false;
      this.button = "Update";
      this.f.statusCd.setValue(this.statusDtls.refStatus.statusCd);
      this.f.statusDesc.setValue(this.statusDtls.refStatus.statusDesc);
      this.f.statusSequence.setValue(this.statusDtls.refStatus.statusSequence);
      this.f.initialState.setValue(this.statusDtls.refStatus.initialState.toString());
      this.f.noRelease.setValue(this.statusDtls.refStatus.noRelease);
      this.f.display.setValue(this.statusDtls.refStatus.display.toString());
      this.f.status.setValue(this.statusDtls.refStatus.status.toString());

      if (this.statusDtls.refStatus.legendColor) {
        this.f.color.setValue(this.statusDtls.refStatus.legendColor)
      }

      if (this.statusDtls.refStatus.initialState) {
        this.f.color.disable();
      }

      this.f.statusSequence.setValidators(Validators.required);
    } else {
      this.f.status.setValue('1');
      this.f.initialState.setValue('0');
      this.f.display.setValue('1');
    }

  }

  private selectLevel() {
    this.selLevelInit = {
      bindLabel: "description",
      bindValue: "levelId",
      searchable: true,
      data: () => {
        return this.wfwRefSvc.getRefLevelList({ typeId: this.typeId });
      }
    }
  }

  onSubmit() {

    this.submitted = true;
    if (this.statusForm.invalid) {
      return;
    }

    const dto = {
      typeId: this.statusDtls.typeId,
      levelId: this.statusDtls.refLevel.levelId,
      statusId: this.statusDtls.refStatus ? this.statusDtls.refStatus.statusId : null,
      statusCd: this.f.statusCd.value,
      statusDesc: this.f.statusDesc.value,
      statusSequence: this.f.statusSequence.value,
      nextLevelId: this.f.nextLevelId.value,
      status: this.f.status.value,
      emailRequired: 0,
      skipApprover: 0,
      initialState: this.f.initialState.value,
      noRelease: this.f.noRelease.value,
      display: this.f.display.value,
      userId: this.authSvc.currentUserValue.userId,
      legendColor: this.f.color.value
    }

    this.wfwRefSvc.addUpdateStatus(dto)
      .subscribe(
        data => {
          this.modalSvc.success("Successful!");
          this.router.navigate(['workflow/configuration/type']);
        },
        error => {
          this.modalSvc.error(error.error);
        });
  }

  setStatusSequence() {
    if (this.f.initialState.value == '1') {
      this.f.statusSequence.setValue(1);
      this.f.color.disable();
    } else {
      this.f.statusSequence.reset(this.statusDtls.refStatus.statusSequence || '');
      this.f.color.enable();
    }
  }

  onCancel() {
    this.router.navigate(['workflow/configuration/level'], { queryParams: { levelId: this.statusDtls.refLevel.levelId, typeId: this.statusDtls.typeId } });
  }
  onReset() {
  }

}
