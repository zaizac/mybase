import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { SelectConfig, ModalService } from 'ui-master';
import { IdmReferenceService, UserMenu, LangDesc } from 'bff-api';
import { NGXLogger } from 'ngx-logger';
import { Router, ActivatedRoute } from '@angular/router';
import { map } from 'rxjs/operators';

@Component({
  selector: 'ui-menu-update',
  templateUrl: './menu-update.component.html'
})
export class MenuUpdateComponent implements OnInit {

  UpdateMenuFrom: FormGroup;
  submitted: boolean = false;
  reset: boolean;
  loading: boolean = false;
  button: string = 'Update';
  result: any;
  selMenuLevelCode1Init: SelectConfig;
  selMenuLevelCode2Init: SelectConfig;
  userMenuLevelDropDown = false;
  menuCode: string;
  menuData: UserMenu;

  constructor(
    private idmRefService: IdmReferenceService,
    private formBuilder: FormBuilder,
    private modalService: ModalService,
    private logger: NGXLogger,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.UpdateMenuFrom = this.formBuilder.group({
      menuCode: [null, [Validators.required]],
      menuDesc: [null, [Validators.required]],
      menuUrl: [null, [Validators.required]],
      menuIcon: [null, [Validators.required]],
      menuLevel: [null, [Validators.required]],
      menuParent: [null, [Validators.required]],
      menuSequence: [null, [Validators.required]],
    });
   }

   get f() { return this.UpdateMenuFrom.controls; }

  onSubmit() {
    this.submitted = true;
    this.loading = true;
    this.reset = false;

    const menu = new UserMenu
    menu.menuCode = this.f.menuCode.value;
    menu.menuDesc = (new LangDesc({ en: this.f.menuDesc.value as string }).getLangDesc());
    menu.menuUrlCd = this.f.menuUrl.value;
    menu.menuIcon = this.f.menuIcon.value;
    menu.menuLevel = this.f.menuLevel.value;
    menu.menuSequence = this.f.menuSequence.value;
    menu.menuParentCode = this.f.menuParent.value;
    if (this.f.menuLevel.value === '1') {
      menu.menuParentCode = 'ROOT';
    }
    this.idmRefService.updateMenu(menu).subscribe(
      data => {
        if(data){
          this.modalService.success('Menu Updated');
          this.router.navigate(['maintenance/userMenu']);
        }
      },
      error => {
        this.logger.error(error.error.message);
        this.modalService.error('ERROR.');
      });
  }

  ngOnInit() {
    this.menuCode = this.route.snapshot.params.cd;
    this.getMenuCode();
  }

  private getMenuCode() {
    this.idmRefService.findMenuBymenuCode(this.menuCode).subscribe(
      data => {
        this.menuData = data;
        this.f.menuCode.setValue(this.menuData.menuCode);
        this.f.menuDesc.setValue(this.menuData.menuDesc.en);
        this.f.menuUrl.setValue(this.menuData.menuUrlCd);
        this.f.menuIcon.setValue(this.menuData.menuIcon);
        this.f.menuLevel.setValue(this.menuData.menuLevel.toString());
        this.f.menuParent.setValue(this.menuData.menuParentCode);
        this.f.menuSequence.setValue(this.menuData.menuSequence);
      },
      error => {
        this.logger.error(error);
      }
    );
  }

  menuParentCode1(level = this.f.menuLevel.value) {
    this.selMenuLevelCode1Init = {
      bindLabel: 'menuCode',
      bindValue: 'menuCode',
      searchable: true,
      data: () => {
        this.result = this.idmRefService.findMenuByMenuLevel(level).pipe(map((res: UserMenu[]) => {
          this.userMenuLevelDropDown = res.constructor.length > 0;
          return res;
        }))
        return this.result;
      }
    }
  }

  menuParentCode2(level = this.f.menuLevel.value) {
    this.selMenuLevelCode2Init = {
      bindLabel: 'menuCode',
      bindValue: 'menuCode',
      searchable: true,
      data: () => {
        this.result = this.idmRefService.findMenuByMenuLevel(level).pipe(map((res: UserMenu[]) => {
          this.userMenuLevelDropDown = res.constructor.length > 0;
          return res;
        }))
        return this.result;
      }
    }
  }

  cancel() {
    this.router.navigate(['maintenance/userMenu']);
  }

}
