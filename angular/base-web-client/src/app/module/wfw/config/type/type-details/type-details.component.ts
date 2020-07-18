import { Component, OnInit } from '@angular/core';
import { DatatableConfig, RouterDataService, ModalService } from 'ui-master';
import { Router, ActivatedRoute, ActivatedRouteSnapshot, UrlSegment } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { WfwReferenceService, AuthService } from 'bff-api';

@Component({
  selector: 'ui-type-details',
  templateUrl: './type-details.component.html'
})
export class TypeDetailsComponent implements OnInit {

  typeDtls: any;
  tableLevelInit: DatatableConfig;
  typeForm: FormGroup;
  isNew: boolean = false;
  submitted: boolean = false;
  reset: boolean;
  button: string = "Create";

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private routerData: RouterDataService,
    private formBuilder: FormBuilder,
    private wfwRefSvc: WfwReferenceService,
    private authSvc: AuthService,
    private modalSvc: ModalService) {

    this.route.queryParams.subscribe(params => {
      this.isNew = params.isNew;
      if (!this.isNew) {
        const stateData = this.router.getCurrentNavigation().extras.state;
        if (stateData) {
          this.typeDtls = stateData;
          this.routerData.addRouterData();
        }
        else if (this.routerData) {
          this.typeDtls = this.routerData.retrieveRouterData();
        }
      }
    });
  }

  ngOnInit() {

    this.typeForm = this.formBuilder.group({
      typeId: [null,],
      typesDesc: [null, [Validators.required]],
      redirectPath: [null, [Validators.required]],
      autoClaim: [null, [Validators.required]],
      status: [null, [Validators.required]],
      tranId: [null, [Validators.required]],
      moduleId: [null,],
    });

    this.setTypeDetails();
    this.tableInitialization();

    console.log('type-details: ', this.route.snapshot);
       
  }

  

  get f() { return this.typeForm.controls; }

  private tableInitialization() {
    if (this.typeDtls) {
      this.tableLevelInit = {
        columns: [
          {
            type: 'expand',
            textAlign: 'Center',
            columnTemplate: {
              id: 'expandTemplate',
              context: {}
            },
            render: (data, cell, row) => {
              const contextData = row.refStatus;
              const expandHeader = {
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
                      expandHeader.columns[8].columnTemplate.context = { color: row.legendColor };
                    }
                  }

                ],
                textWrap: {
                  allowWrap: true,
                  mode: 'Both'
                },
                initialPage: {
                  pageSizes: false
                }
              };
              const levelId = row.levelId;
              this.tableLevelInit.columns[0].columnTemplate.context = Object.assign({}, {
                header: expandHeader,
                data: contextData,
                levelId: levelId
              });
            }
          },
          { field: 'SlNo', headerText: 'No.', allowSorting: false, width: '6%' },
          {
            field: 'description',
            headerText: 'Level',
            width: '25%'
          },
          {
            field: 'flowNo',
            headerText: 'Flow ID',
            width: '12%'
          },
          {
            field: 'status',
            headerText: 'Is Active',
            width: '12%',
            render: (data, cell, row) => {
              cell.textContent = data === 1 ? 'Yes' : 'No';
            }
          },
          {
            field: 'roles',
            headerText: 'Roles',
            width: '40%'
          }
        ],
        textWrap: {
          allowWrap: true,
          mode: 'Both'
        },
        initialPage: {
          pageSizes: false, pageCount: 5
        },
        data: dtRequest => {
          dtRequest.typeId = this.typeDtls.typeId;
          return this.wfwRefSvc.getRefLevelPagination(dtRequest);
        }
      };
    }
  }

  setTypeDetails() {
    if (this.typeDtls) {
      this.button = 'Update';
      this.f.typeId.setValue(this.typeDtls.typeId);
      this.f.typesDesc.setValue(this.typeDtls.description);
      this.f.redirectPath.setValue(this.typeDtls.redirectPath);
      this.f.autoClaim.setValue(this.typeDtls.autoClaim.toString());
      this.f.status.setValue(this.typeDtls.status.toString());
      this.f.tranId.setValue((this.typeDtls.tranId));
      this.f.moduleId.setValue(this.typeDtls.moduleId);
    } else {
      this.f.autoClaim.setValue('0');
      this.f.status.setValue('1');
    }
  }

  onSubmit() {

    this.submitted = true;

    if (this.typeForm.invalid) {
      return;
    }

    const dto = {
      typeId: this.typeDtls ? this.typeDtls.typeId : null,
      description: this.f.typesDesc.value,
      redirectPath: this.f.redirectPath.value,
      autoClaim: this.f.autoClaim.value,
      status: this.f.status.value,
      tranId: this.f.tranId.value,
      moduleId: this.f.moduleId.value,
      userId: this.authSvc.currentUserValue.userId
    };

    this.wfwRefSvc.addUpdateType(dto)
      .subscribe(
        data => {
          this.modalSvc.success('Successful!');
          this.router.navigate(['workflow/configuration']);
        },
        error => {
          this.modalSvc.error(error.error);
        });
  }

  // reset the form data
  onReset() {
    // this.submitted = false;
    // this.reset = true;
    // this.getProfile();
  }

  rowLevelSelected(row) {
    this.router.navigate(['workflow/configuration/level'], { queryParams: { levelId: row.levelId, typeId: row.typeId } });
  }

  rowStatusSelected(row) {
    let typeId;
    if (row.typeId) {
      typeId = row.typeId;
    } else {
      typeId = this.typeDtls.typeId;
    }
    this.router.navigate(['workflow/configuration/status'], { queryParams: { levelId: row.levelId, typeId: typeId, statusId: row.statusId } });
  }

  cancel() {
    this.router.navigate(['workflow/configuration']);
  }

}
