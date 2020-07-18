import { Component, OnInit, OnDestroy } from '@angular/core';
import { IdmReferenceService, OauthClientDetails } from 'bff-api';
import { NGXLogger } from 'ngx-logger';
import { ModalService, DatatableConfig } from 'ui-master';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router, NavigationEnd } from '@angular/router';

@Component({
  selector: 'ui-mtnc-oauth-client-details',
  templateUrl: './mtnc-oauth-client-details.component.html'
})
export class MtncOauthClientDetailsComponent implements OnInit, OnDestroy {

  mtncOCDForm: FormGroup;
  noDataConfig: DatatableConfig;
  ocdList: OauthClientDetails[];
  submitted = false;
  reset: boolean;
  loading = false;
  buttonCreateUpdate = 'Create';
  mySubscription: any;

  constructor(private idmReferenceService: IdmReferenceService,
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
    this.idmReferenceService.retrieveOauthClientDetails()
      .subscribe(data => {
        this.ocdList = data;
      });

    this.noDataConfig = {
      columns: [
        { field: 'SlNo', headerText: 'No.', width: '10%' },
        { field: 'clientId', headerText: 'Client Id', width: '15%' },
        { field: 'resourceIds', headerText: 'Resource Ids' },
        { field: 'clientSecret', headerText: 'Client Secret' },
        { field: 'scope', headerText: 'Scope' },
        { field: 'authorizedGrantTypes', headerText: 'Authorized Grant Type' },
        { field: 'webServerRedirectUri', headerText: 'Web Server Redirect URI', visible: false },
        { field: 'authorities', headerText: 'Authorities', visible: false },
        { field: 'accessTokenValidity', headerText: 'Access Token Valid', visible: false },
        { field: 'refreshTokenValidity', headerText: 'Refresh Token Valid', visible: false },
        { field: 'additionalInformation', headerText: 'Additional Information', visible: false },
        { field: 'autoapprove', headerText: 'Auto Approve', visible: false },
        {
          field: '', headerText: 'Action', customAttributes: {
            'no-row-click': true
          },
          columnTemplate: {
            id: 'actionDelete',
          },
          render: (data, cell, row) => {
            this.noDataConfig.columns[12].columnTemplate.context = {
              row
            };
          }
        }
      ]
    };

    this.mtncOCDForm = new FormGroup({
      clientId: new FormControl(null, [Validators.required]),
      accessTokenValidity: new FormControl(),
      additionalInformation: new FormControl(),
      authorities: new FormControl(),
      authorizedGrantTypes: new FormControl(),
      autoapprove: new FormControl(),
      clientSecret: new FormControl(),
      refreshTokenValidity: new FormControl(),
      resourceIds: new FormControl(),
      scope: new FormControl(),
      webServerRedirectUri: new FormControl()
    });
  }

  get f() { return this.mtncOCDForm.controls; }


  ngOnDestroy() {
    if (this.mySubscription) {
      this.mySubscription.unsubscribe();
    }
  }

  onSubmit() {
    if (this.mtncOCDForm.valid) {
      this.updateList();
    } else {
      this.modalSvc.error("ERROR.");
    }
  }

  cancel() {
    this.router.navigate(['maintenance/oauthClientDetails']);
  }


    // reset the form data
  onReset() {
    this.reset = true;
    this.submitted = false;
    this.noDataConfig.redraw();
    this.mtncOCDForm.reset();
  }


  updateList() {
    this.submitted = true;
    this.loading = true;
    this.reset = false;

    const ocdToUpdate = new OauthClientDetails({
      clientId: this.mtncOCDForm.value.clientId as string,
      accessTokenValidity: this.mtncOCDForm.value.accessTokenValidity as number,
      additionalInformation: this.mtncOCDForm.value.additionalInformation as string,
      authorities: this.mtncOCDForm.value.authorities as string,
      authorizedGrantTypes: this.mtncOCDForm.value.authorizedGrantTypes as string,
      autoapprove: this.mtncOCDForm.value.autoapprove as string,
      clientSecret: this.mtncOCDForm.value.clientSecret as string,
      refreshTokenValidity: this.mtncOCDForm.value.refreshTokenValidity as number,
      resourceIds: this.mtncOCDForm.value.resourceIds as string,
      scope: this.mtncOCDForm.value.scope as string,
      webServerRedirectUri: this.mtncOCDForm.value.webServerRedirectUri as string
    });

    this.logger.debug('update list: ', ocdToUpdate);
    // update list
    this.ocdList.push(ocdToUpdate);

    // update db
    this.idmReferenceService.updateOauthClientDetails(this.ocdList).subscribe(
      data => {
      this.logger.debug('Print data length : ', data.length, ' VS list length : ', this.ocdList.length);
      if (data.length >= this.ocdList.length) {
        this.noDataConfig.redraw();
      } else {
        this.ocdList.splice(this.ocdList.indexOf(ocdToUpdate), 1);
      }
    },
    error => {
      this.logger.error(error.error.message);
      this.modalSvc.error("ERROR.");
    });
  }

  deleteRow(selectedRow: any) {
    this.logger.debug('delete Row', selectedRow);
    this.modalSvc.confirm('Do you want to delete this ClientId: ' + selectedRow.clientId + '?').then(confirm => {
      if (confirm) {
        this.idmReferenceService.deleteOauthClientDetails(selectedRow).subscribe(res => {
          if (res) {
            this.logger.debug('Deleting ', selectedRow);
            this.ocdList.splice(this.ocdList.indexOf(selectedRow), 1);
            this.noDataConfig.redraw();
          }
        });
      }
    });
  }

}
