import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { IdmReferenceService, IdmConfigDto } from 'bff-api';
import { ModalService } from 'ui-master';
import { NGXLogger } from 'ngx-logger';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'ui-user-config-update',
  templateUrl: './user-config-update.component.html'
})
export class UserConfigUpdateComponent implements OnInit {

  userConfigFrom: FormGroup;
  submitted: boolean = false;
  reset: boolean;
  loading: boolean = false;
  button: string = "Update";
  configCode: string;
  userConfig: IdmConfigDto;
  configId: any;

  constructor(
    private formBuilder: FormBuilder,
    private idmRefService: IdmReferenceService,
    private modalService: ModalService,
    private logger: NGXLogger,
    private router: Router,
    private route: ActivatedRoute
  ) { 
    this.userConfigFrom = this.formBuilder.group({
      userCfgCode: [null, [Validators.required]],
      userCfgDesc: [null, [Validators.required]],
      userCfgVal: [null, [Validators.required]]
    });
  }

  get f() { return this.userConfigFrom.controls; }

  onSubmit() {
    this.submitted = true;
    this.loading = true;
    this.reset = false;

    const uConfig = new IdmConfigDto
    uConfig.configId = this.configId;
    uConfig.configCode = this.f.userCfgCode.value;
    uConfig.configDesc = this.f.userCfgDesc.value;
    uConfig.configVal = this.f.userCfgVal.value;

    this.idmRefService.updateUserConfig(uConfig).subscribe(
      data => {
        if (data){
          this.modalService.success("User Configuration Updated");
          this.router.navigate(['maintenance/userConfig']);
        }
      },
      error => {
        this.logger.error(error.error.message);
        this.modalService.error("ERROR.");
      });
  }

  ngOnInit() {
    this.configCode = this.route.snapshot.params.cd;
    this.getConfigCode();
  }

  private getConfigCode() {
    this.idmRefService.findConfigByConfigCode(this.configCode).subscribe(
      data => {
        this.userConfig = data;
        this.configId = this.userConfig.configId;
        this.f.userCfgCode.setValue(this.userConfig.configCode);
        this.f.userCfgDesc.setValue(this.userConfig.configDesc);
        this.f.userCfgVal.setValue(this.userConfig.configVal);
      },
      error => {
        this.logger.error(error);
      }
    );
  }

  cancel() {
    this.router.navigate(['maintenance/userConfig']);
  }

}
