import { Component, OnInit } from '@angular/core';
import { UserType, IdmReferenceService, LangDesc } from 'bff-api';
import { Router, ActivatedRoute } from '@angular/router';
import { NGXLogger } from 'ngx-logger';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ModalService } from 'ui-master';

@Component({
  selector: 'ui-user-type-update',
  templateUrl: './user-type-update.component.html',
  styleUrls: ['./user-type-update.component.scss']
})
export class UserTypeUpdateComponent implements OnInit {

  submitted: boolean = false;
  reset: boolean;
  loading: boolean = false;
  button: string = 'Update';
  typeCd: string;
  currUserType: UserType;
  userType: FormGroup;
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private idmService: IdmReferenceService,
    private formBuilder: FormBuilder,
    private modalService: ModalService,
    private logger: NGXLogger) {
    this.userType = this.formBuilder.group({
      userTypeCode: [null, [Validators.required]],
      userTypeDesc: [null, [Validators.required]],
      emailAsUserId: [null, [Validators.required]]
    });
  }

  get f() { return this.userType.controls; }

  ngOnInit() {
    this.typeCd = this.route.snapshot.params.cd;

    if (this.typeCd !== 'NEW') {
      this.getUserType();
    } else {
      this.f.userTypeCode.setValue(null);
      this.f.userTypeDesc.setValue(null);
      this.f.emailAsUserId.setValue(null);
      this.button = 'Create';
    }

  }


  private getUserType() {
    this.idmService.searchByUserTypeCd(this.typeCd).subscribe(
      data => {
        this.currUserType = data;

        this.f.userTypeCode.setValue(this.currUserType.userTypeCode);
        this.f.userTypeDesc.setValue(this.currUserType.userTypeDesc.en);
        this.f.emailAsUserId.setValue(this.currUserType.emailAsUserId.toString());
      },
      error => {
        this.logger.error(error);
      }
    );
  }

  onSubmit() {
    this.submitted = true;
    this.loading = true;
    this.reset = false;

    const type = new UserType({
      userTypeCode: this.f.userTypeCode.value,
      userTypeDesc: new LangDesc({ en: this.f.userTypeDesc.value }),
      emailAsUserId: this.f.emailAsUserId.value
    });

    this.idmService.updateUserType(type)
      .subscribe(
        data => {
          if (data) {
            this.modalService.success('User Type ' + this.button + 'd Successfully');
            this.router.navigate(['maintenance/userType']);
          }
        },
        error => {
          this.logger.error(error.error.message);
          this.modalService.error('Failed to process the transaction. Please contact system administrator.');
        }
      );
  }

  cancel() {
    this.router.navigate(['maintenance/userType']);
  }

  onReset() {
    this.f.userTypeCode.setValue(null);
    this.f.userTypeDesc.setValue(null);
    this.f.emailAsUserId.setValue(null);
  }
}
