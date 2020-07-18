import { Component, OnInit, ViewChild, TemplateRef, OnDestroy } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { WfwReferenceService, AuthService, ReferenceService, IdmReferenceService } from 'bff-api';
import { ModalService, SelectConfig, DatatableConfig, RouterDataService } from 'ui-master';

@Component({
  selector: 'ui-level-details',
  templateUrl: './level-details.component.html'
})
export class LevelDetailsComponent implements OnInit, OnDestroy {


  levelDtls: any;
  levelForm: FormGroup;
  actionForm: FormGroup;
  isNew: boolean = true;
  submitted: boolean = false;
  reset: boolean = false;
  selRolesInit: SelectConfig;
  button: string = "Create";
  tableInit: DatatableConfig;
  tableStatusInit: DatatableConfig;
  data: any = [];
  statusList: any = null;


  status = new FormControl();

  constructor(
    private router: Router,
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private routerData: RouterDataService,
    private wfwRefSvc: WfwReferenceService,
    private modalSvc: ModalService,
    private authSvc: AuthService,
    private refSvc: ReferenceService,
    private idmRefSvc: IdmReferenceService) {

    this.levelForm = this.formBuilder.group({
      type: [null, [Validators.required]],
      level: [null, [Validators.required]],
      flowNo: [null],
      status: [null, [Validators.required]],
      roles: [null, [Validators.required]]
    });

    this.actionForm = this.formBuilder.group({
      actionType: null,
      actionLevel: null,
      actionName: [null, [Validators.required]],
      redirectPath: [null, [Validators.required]],
      status: [null, [Validators.required]]
    });
  }

  ngOnInit() {
    this.data = [];


    this.route.queryParams.subscribe(params => {
      this.wfwRefSvc.getRefConfig(params).subscribe(
        data => {
          this.levelDtls = data;

          if (this.levelDtls.refLevel) {
            if (sessionStorage.getItem("levelActions")) {
              this.data = JSON.parse(sessionStorage.getItem("levelActions"));
            } else {
              this.data = this.levelDtls.refLevel.refTypeActionList;
            }
            this.wfwRefSvc.getRefStatusList({ levelId: this.levelDtls.refLevel.levelId }).subscribe(status => {
              this.statusList = status
            });
          }

          this.setTypeDetails();
        });
    });

    this.setSelectRoles();
    this.tableInitialization();
    this.tableStatusInitialization();
  }

  get f() { return this.levelForm.controls; }
  get a() { return this.actionForm.controls; }

  private tableInitialization() {
    this.tableInit = {
      columns: [
        // { field: 'SlNo', headerText: 'No.', allowSorting: false, width: 100, allowEditing: false },
        { field: 'typeActionId', headerText: 'No.', isPrimaryKey: true, width: 100, visible: false },
        {
          field: 'actionName',
          headerText: 'Action',
          width: 120, validationRules: { required: true },
          render: (data, cell, row) => {
            if (data) {
              cell.textContent = data.toUpperCase();
            }
          }
        },
        {
          field: 'redirectPath',
          headerText: 'Redirect Path',
          width: 120, validationRules: { required: true }
        },
        {
          field: 'status',
          headerText: 'Is Active',
          width: 120,
          editType: 'booleanedit',
          displayAsCheckBox: true,
          type: 'boolean',
        },
        {
          headerText: '',
          width: 60,
          commands: [
            { type: 'Edit', buttonOption: { iconCss: ' e-icons e-edit', cssClass: 'e-flat' } },
            { type: 'Save', buttonOption: { iconCss: 'e-icons e-update', cssClass: 'e-flat' } },
            { type: 'Cancel', buttonOption: { iconCss: 'e-icons e-cancel-icon', cssClass: 'e-flat' } }
          ]
        }
      ],
      editSettings: { allowEditing: true, allowAdding: true, allowDeleting: true, mode: 'Normal' },
      toolbar: ['Add', 'Cancel'],
      initialPage: {
        pageSizes: false
      }
    };
  }

  private tableStatusInitialization() {
    this.tableStatusInit = {
      columns: [
        { field: 'SlNo', headerText: 'No.', allowSorting: false, width: '6%' },
        { field: 'statusCd', headerText: 'Status Code', width: '10%' },
        { field: 'statusDesc', headerText: 'Description', width: '16%' },
        { field: 'statusSequence', headerText: 'Sequence', width: '12%' },
        {
          field: 'nextLevelId', headerText: 'Next Level', width: '12%',
          render: (data, cell, row) => {
            if (data > 0) {
              var level;
              this.wfwRefSvc.getRefLevelList({ levelId: row.nextLevelId })
                .subscribe(data => {
                  level = data[0];
                  cell.textContent = level.description;
                });
            }
            else {
              cell.textContent = 'N/A';
            }
          }
        },
        { field: 'noRelease', headerText: 'No. of Released', width: '15%' },
        {
          field: 'display', headerText: 'Display', width: '12%',
          render: (data, cell, row) => {
            cell.textContent = data == 1 ? 'Yes' : 'No';
          }
        },
        {
          field: 'status', headerText: 'Active', width: '12%',
          render: (data, cell, row) => {
            cell.textContent = data == 1 ? 'Yes' : 'No';
          }
        },
        {
          field: '',
          headerText: 'Legend',
          width: '10%',
          columnTemplate: {
            id: 'legendTemplate',
            context: {}
          },
          render: (data, cell, row) => {
            this.tableStatusInit.columns[8].columnTemplate.context = { color: row.legendColor };
          }
        }
      ],
      initialPage: {
        pageSizes: false, pageCount: 5
      }
    };
  }

  setTypeDetails() {
    this.f.type.setValue(this.levelDtls.description);
    this.f.type.disable();
    if (this.levelDtls.refLevel) {
      this.isNew = false;
      this.button = "Update";
      this.f.level.setValue(this.levelDtls.refLevel.description);
      this.f.status.setValue(this.levelDtls.refLevel.status.toString());
      this.f.flowNo.setValue((this.levelDtls.refLevel.flowNo));
      this.f.roles.setValue(this.levelDtls.refLevel.roles);
    } else {
      this.f.status.setValue('1');
    }
  }

  onSubmit() {

    if (this.levelForm.invalid) {
      return;
    }

    const dto = {
      typeId: this.levelDtls.typeId,
      levelId: this.levelDtls.refLevel ? this.levelDtls.refLevel.levelId : null,
      description: this.f.level.value,
      flowNo: this.f.flowNo.value,
      status: this.f.status.value,
      roles: this.f.roles.value,
      userId: this.authSvc.currentUserValue.userId,
      refTypeActionList: this.data
    }

    this.wfwRefSvc.addUpdateLevel(dto)
      .subscribe(
        data => {
          this.modalSvc.success("Successful " + this.button + "d!");
          this.router.navigate(['workflow/configuration/type']);
        },
        error => {
          this.modalSvc.error(error.error.errorMessage);
        });
  }


  setSelectRoles() {
    this.selRolesInit = {
      bindLabel: "roleDesc.en",
      bindValue: "roleCode",
      searchable: true,
      multiple: true,
      data: () => {
        return this.idmRefSvc.retrieveUserRoles();
      }
    };
  }


  cancel() {
    this.actionForm.reset();
    this.modalSvc.dismiss()
  }

  onReset() {
    sessionStorage.removeItem("levelActions");
    this.ngOnInit();
  }

  rowStatusSelected(row) {
    var sttsId = null;
    if (row) {
      sttsId = row.statusId;
    }
    this.router.navigate(['workflow/configuration/status'],
      {
        queryParams: {
          levelId: this.levelDtls.refLevel.levelId,
          typeId: this.levelDtls.typeId,
          statusId: sttsId
        }
      });
  }

  onAction() {
    sessionStorage.setItem("levelActions", JSON.stringify(this.data));

  }

  ngOnDestroy(): void {
    sessionStorage.removeItem("levelActions");
  }

}
