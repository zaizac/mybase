import { Component, OnInit, SimpleChanges } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { IdmReferenceService, UserMenu, LangDesc } from 'bff-api';
import { ModalService, SelectConfig, DatatableConfig } from 'ui-master';
import { NGXLogger } from 'ngx-logger';
import { map } from 'rxjs/operators';
import { Router } from '@angular/router';

@Component({
  selector: 'ui-menu-add',
  templateUrl: './menu-add.component.html',
  styles: []
})
export class MenuAddComponent implements OnInit {

  menuFrom: FormGroup;
  submitted: boolean = false;
  reset: boolean;
  loading: boolean = false;
  result: any;
  selMenuLevelCode1Init: SelectConfig;
  selMenuLevelCode2Init: SelectConfig;
  noDataConfig: DatatableConfig;
  menuDT: UserMenu[] = new Array();
  menuList: UserMenu[] = new Array();
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
      menuCode: [null],
      menuDesc: [null],
      menuLevel: [null],
      menuParent: [null],
    });
  }

  get f() { return this.menuFrom.controls; }

  onSubmit() {

    this.submitted = true;
    this.loading = true;
    this.reset = false;

    this.menuList.length = 0;
    this.noDataConfig.redraw();

    const menuList = new UserMenu();
    menuList.menuCode = this.f.menuCode.value;
    menuList.menuLevel = this.f.menuLevel.value;
    menuList.menuParentCode = this.f.menuParent.value;

    // Triggers the datatable search
    this.idmRefService.searchMenu(menuList)
      .subscribe(data => {
        if (data) {
          // reassign value - maintain roleList reference
          data.forEach(element => {
            this.menuList.push(element);
          });
        }
        this.noDataConfig.redraw();
      });
    // Triggers the datatable search
    this.noDataConfig.refresh();
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

  public ngOnInit(): void {
    this.f.menuLevel.setValue('1');
    this.idmRefService.findAllMenuList()
      .subscribe(
        data => {
          this.menuDT = data;
          if (data) {
            // reassign value - maintain roleList reference
            data.forEach(element => {
              this.menuList.push(element);
            });
          }
          this.noDataConfig.redraw();
        });

    this.tableInitialization();
  }

  private tableInitialization() {
    this.noDataConfig = {
      columns: [
        { field: 'SlNo', headerText: 'No.', width: '10%' },
        { field: 'menuCode', headerText: 'Menu Code', width: '25%' },
        { field: 'menuParentCode', headerText: 'Menu Parent Code' },
        { field: 'menuDesc.en', headerText: 'Menu Description' },
        { field: 'menuLevel', headerText: 'Menu Level' },
        { field: 'menuSequence', headerText: 'Menu Sequence Level' },
        {
          field: '', headerText: 'Action', customAttributes: {
            'no-row-click': true
          },
          columnTemplate: {
            id: 'actionDelete',
          },
          render: (data, cell, row) => {
            this.noDataConfig.columns[6].columnTemplate.context = {
              row
            };
          }
        }
      ]
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
    this.router.navigate(['maintenance/userMenu']);
  }

  delete(selectedRow: any) {
    this.modalService.confirm('Do you want to delete the Menu: ' + selectedRow.menuCode + '?').then(confirm => {
      if (confirm) {
        this.idmRefService.deleteMenu(selectedRow).subscribe(res => {
          if (res) {
            this.logger.debug('Deleting ', selectedRow);
            this.menuDT.splice(this.menuDT.indexOf(selectedRow), 1);
            this.noDataConfig.redraw();
          }
        });
      }
    });
  }

  rowSelected($event) {
    this.selectMenuCode = $event;
    this.router.navigate(['maintenance/userMenu/update', this.selectMenuCode.menuCode]);
  }
}
