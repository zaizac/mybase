import { Component, OnInit, ViewChild } from '@angular/core';
import { DatatableConfig } from 'projects/ui-master/src/lib/component/datatable/datatable.config';
import { FileConfig } from 'projects/ui-master/src/lib/component/file/model/file.config';
import { SIMPLEDATA } from '../mockData';

@Component({
  selector: 'app-dt-template',
  templateUrl: './dt-template.component.html',
  styleUrls: ['./dt-template.component.scss']
})
export class DtTemplateComponent implements OnInit {
  headerConfig: DatatableConfig;
  headerConfig2: DatatableConfig;
  headerConfig3: DatatableConfig;
  headerConfig4: DatatableConfig;
  dtData: any = SIMPLEDATA;
  buttonConfig = {
    buttonType: 'primary',
    icon: 'fa fa-search',
    type: 'submit',
    text: 'Search'
  };

  @ViewChild('testTemplate') public testTemplate: any;

  refDoc = {
    compulsary: false,
    dimensionCompulsary: false,
    docId: 77,
    maxHeight: 0,
    maxWidth: 0,
    size: 5000000,
    title: 'PROFILE PICTURE',
    trxnNo: 'PROFPIC',
    type: 'IMAGE/GIF, IMAGE/JPEG, IMAGE/PNG'
  } as FileConfig;

  constructor() {}

  ngOnInit() {
    this.headerConfig = {
      columns: [
        { field: 'SlNo', headerText: 'No.', width: '20%' },
        { field: 'name', headerText: 'Name', width: '30%' },
        {
          field: '',
          headerText: 'Detail',
          width: '50%',
          render: (data, cell, row) => {
            cell.innerHTML =
              '<div><ul>Details:<li>Gender:' +
              (row.gender || '-') +
              '</li><li>Age: ' +
              (row.age || '-') +
              '</li><li>Company: ' +
              (row.company || '-') +
              '</li></ul></div>';
          }
        }
      ]
    };

    this.headerConfig2 = {
      columns: [
        { field: 'SlNo', headerText: 'No.', width: '20%' },
        { field: 'name', headerText: 'Name', width: '30%' },
        {
          field: '',
          headerText: 'Detail',
          width: '50%',
          columnTemplate: {
            id: 'myTemplate',
            context: {}
          },
          render: (data, cell, row) => {
            const context = {
              buttonType: 'primary',
              icon: 'fa fa-search',
              type: 'submit',
              text: 'Search'
            };
            const ind = Number(cell.getAttribute('index'));
            if (ind === 1) {
              context.buttonType = 'danger';
              context.icon = 'fa fa-exclamation-triangle';
              context.text = 'Hazard';
            }

            if (ind === 2) {
              context.buttonType = 'warning';
              context.icon = 'fa fa-battery-quarter';
              context.text = 'Caution';
            }

            if (ind === 3) {
              context.buttonType = 'success';
              context.icon = 'fa fa-cloud-download';
              context.text = 'Download';
            }

            this.headerConfig2.columns[2].columnTemplate.context = ind === 0 ? this.refDoc : context;
            this.headerConfig2.columns[2].columnTemplate.id = ind === 0 ? 'mySecTemplate' : 'myTemplate';
          }
        }
      ]
    };

    this.headerConfig3 = {
      columns: [
        { field: 'SlNo', headerText: 'No.', width: '20%' },
        { field: 'name', headerText: 'Name', width: '30%' },
        {
          field: '',
          headerText: 'Using template field',
          width: '50%'
          // template: this.testTemplate
        }
      ]
    };

    this.headerConfig4 = {
      columns: [
        { field: 'SlNo', headerText: 'No.', width: '20%' },
        { field: 'name', headerText: 'Name', width: '30%' },
        {
          field: '',
          customAttributes: {
            'no-row-click': true
          },
          headerText: 'Detail',
          width: '50%',
          columnTemplate: {
            id: 'actionTemplate',
            context: {}
          },
          render: (data, cell, row) => {}
        }
      ]
    };
  }
}
