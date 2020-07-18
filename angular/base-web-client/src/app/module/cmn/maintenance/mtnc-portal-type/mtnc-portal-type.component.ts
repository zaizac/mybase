import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { DatatableConfig, ModalService } from 'ui-master';
import { ReferenceService, LangDesc, PortalType } from 'bff-api';
import { NGXLogger } from 'ngx-logger';
import { Router, NavigationEnd } from '@angular/router';

@Component({
  selector: 'ui-mtnc-portal-type',
  templateUrl: './mtnc-portal-type.component.html'
})
export class MtncPortalTypeComponent implements OnInit, OnDestroy {

  mtncPortalTypeForm: FormGroup;
  noDataConfig: DatatableConfig;
  portalTypeList: PortalType[];
  submitted = false;
  reset: boolean;
  loading = false;
  buttonCreateUpdate = 'Create';
  mySubscription: any;

  constructor(private referenceService: ReferenceService,
              private logger: NGXLogger,
              private router: Router,
              public modalSvc: ModalService) {
                this.router.routeReuseStrategy.shouldReuseRoute = () => {
                  return false;
                };
                this.mySubscription = this.router.events.subscribe((event) => {
                  if (event instanceof NavigationEnd) {
                    // Trick the Router into believing it's last link wasn't previously loaded
                    this.router.navigated = false;
                  }
                });
              }

  ngOnInit() {
    this.referenceService.retrievePortalTypeAll()
      .subscribe(data => {
        this.portalTypeList = data;
      });

    this.noDataConfig = {
      columns: [
        { field: 'SlNo', headerText: 'No.', width: '10%' },
        { field: 'portalTypeCode', headerText: 'Portal Type Code', width: '30%' },
        { field: 'portalTypeDesc.en', headerText: 'Portal Type Description' },
        {
          field: '', headerText: 'Action', customAttributes: {
            'no-row-click': true
          },
          columnTemplate: {
            id: 'actionDelete',
          },
          render: (data, cell, row) => {
            this.noDataConfig.columns[3].columnTemplate.context = {
              row
            };
          }
        }
      ]
    };

    this.mtncPortalTypeForm = new FormGroup({
      portalTypeCode: new FormControl(null, [Validators.required]),
      portalTypeDesc: new FormControl()
    });
  }

  get f() { return this.mtncPortalTypeForm.controls; }


  ngOnDestroy() {
    if (this.mySubscription) {
      this.mySubscription.unsubscribe();
    }
  }

  onSubmit() {
    if (this.mtncPortalTypeForm.valid) {
      this.updateList();
    } else {
      this.modalSvc.error("ERROR.");
    }
  }

  cancel() {
    this.router.navigate(['maintenance/portalType']);
  }


  // reset the form data
  onReset() {
    this.reset = true;
    this.submitted = false;
    this.noDataConfig.redraw();
    this.mtncPortalTypeForm.reset();
  }

  updateList() {
    if (this.mtncPortalTypeForm.value.portalTypeCode && this.mtncPortalTypeForm.value.portalTypeDesc) {
      // update list
      this.portalTypeList.push({
        portalTypeCode: this.mtncPortalTypeForm.value.portalTypeCode as string,
        portalTypeDesc: (new LangDesc({ en: this.mtncPortalTypeForm.value.portalTypeDesc as string }).getLangDesc())
      });

      // update db
      this.referenceService.updatePortalType(this.portalTypeList).subscribe(
        data => {
        this.logger.debug('Print data length : ', data.length, ' VS list length : ', this.portalTypeList.length);
        if (data.length >= this.portalTypeList.length) {
          this.noDataConfig.redraw();
        } else {
          this.portalTypeList.splice(this.portalTypeList.indexOf({
            portalTypeCode: this.mtncPortalTypeForm.value.portalTypeCode,
            portalTypeDesc: (new LangDesc({ en: this.mtncPortalTypeForm.value.portalTypeDesc as string }).getLangDesc())
          }), 1);
        }
      },
      error => {
        this.logger.error(error.error.message);
        this.modalSvc.error("ERROR.");
      });
    }
  }

  deleteRow(selectedRow: any) {
    this.logger.debug('delete Row', selectedRow);
    this.modalSvc.confirm('Do you want to delete this Portal Type Code : ' + selectedRow.portalTypeCode + '?').then(confirm => {
      if (confirm) {
        this.referenceService.deletePortalType(selectedRow).subscribe(res => {
          if (res) {
            this.logger.debug('Deleting ', selectedRow);
            this.portalTypeList.splice(this.portalTypeList.indexOf(selectedRow), 1);
            this.noDataConfig.redraw();
          }
        });
      }
    });
  }

}
