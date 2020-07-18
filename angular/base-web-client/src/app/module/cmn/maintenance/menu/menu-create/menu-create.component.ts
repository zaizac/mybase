import { Component, OnInit, SimpleChanges } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { IdmReferenceService, UserMenu, LangDesc } from 'bff-api';
import { ModalService, SelectConfig, DatatableConfig } from 'ui-master';
import { NGXLogger } from 'ngx-logger';
import { map } from 'rxjs/operators';
import { Router } from '@angular/router';

@Component({
  selector: 'ui-menu-create',
  templateUrl: './menu-create.component.html'
})
export class MenuCreateComponent implements OnInit {

  menuFrom: FormGroup;
  submitted = false;
  reset: boolean;
  loading = false;
  button = 'Create';
  result: any;
  selMenuLevelCode1Init: SelectConfig;
  selMenuLevelCode2Init: SelectConfig;
  noDataConfig: DatatableConfig;
  menuDT: UserMenu[];
  userMenuLevelDropDown = false;
  selectMenuCode: any;

  constructor(
    private idmRefService: IdmReferenceService,
    private formBuilder: FormBuilder,
    private modalService: ModalService,
    private logger: NGXLogger,
    private router: Router
  ) {
    this.menuFrom = this.formBuilder.group({
      menuCode: [null, [Validators.required]],
      menuDesc: [null, [Validators.required]],
      menuUrl: [null, [Validators.required]],
      menuIcon: [null, [Validators.required]],
      menuLevel: [null, [Validators.required]],
      menuParent: [null, [Validators.required]],
    });
  }

  get f() { return this.menuFrom.controls; }

  onSubmit() {
    this.submitted = true;
    this.loading = true;
    this.reset = false;

    const menu = new UserMenu();
    menu.menuCode = this.f.menuCode.value;
    menu.menuDesc = (new LangDesc({ en: this.f.menuDesc.value as string }).getLangDesc());
    menu.menuUrlCd = this.f.menuUrl.value;
    menu.menuIcon = this.f.menuIcon.value;
    menu.menuLevel = this.f.menuLevel.value;
    menu.menuParentCode = this.f.menuParent.value;
    if (this.f.menuLevel.value === '1') {
      menu.menuParentCode = 'ROOT';
    }

    this.menuDT.push(menu);

    this.idmRefService.createMenu(menu).subscribe(
      data => {
        if (data) {
          this.modalService.success('Menu Created');
          this.router.navigate(['maintenance/userMenu']);
          //this.noDataConfig.refresh();
          //this.onReset();
        } else {
          this.menuDT.splice(this.menuDT.indexOf({
            menuCode: this.f.menuCode.value,
            menuDesc: this.f.menuDesc.value,
            menuLevel: this.f.menuLevel.value
          }), 1);
        }
      },
      error => {
        this.logger.error(error.error.message);
        this.modalService.error('ERROR.');
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
        }));
        return this.result;
      }
    };
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
        }));
        return this.result;
      }
    };
  }

  // tslint:disable-next-line: use-life-cycle-interface
  ngOnChanges(changes: SimpleChanges) {
    this.logger.debug(changes);
    if (changes.menuDT) {
      this.noDataConfig.redraw();
    }
  }

  // reset the form data
  onReset() {
    this.reset = true;
    this.submitted = false;
    this.menuFrom.reset();
    this.router.navigate(['maintenance/userMenu/create']);
  }

  cancel() {
    this.router.navigate(['maintenance/userMenu']);
  }

  ngOnInit() {
    this.f.menuLevel.setValue('1');
    this.idmRefService.findAllMenuList()
      .subscribe(
        data => {
          this.menuDT = data;
        });
  }

}
