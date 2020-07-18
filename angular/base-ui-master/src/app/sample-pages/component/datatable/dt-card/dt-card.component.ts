import { Component, OnInit } from '@angular/core';
import { DatatableConfig } from 'projects/ui-master/src/lib/component/datatable/datatable.config';
import { mockServerSidePaginationResponse } from '../mockData';

@Component({
  selector: 'app-dt-card',
  templateUrl: './dt-card.component.html',
  styleUrls: ['./dt-card.component.scss']
})
export class DtCardComponent implements OnInit {
  headerConfig: DatatableConfig;
  headerConfig2: DatatableConfig;
  headerConfig3: DatatableConfig;

  constructor() { }

  ngOnInit() {
    this.headerConfig = {
      columns: [
        { field: 'name', headerText: 'Name' },
        { field: 'gender', headerText: 'Gender' },
        { field: 'company', headerText: 'Company' }
      ],
      cardSetting: {
        cardPerRow: 4,
        template: {
          id: 'cardRowTemplate',
          context: {
            title: "Member's Detail",
            showImage: true,
            showTitle: true,
            cardImage: 'assets/default/images/no_image.jpg'
          }
        }
      },
      data: request => {
        return mockServerSidePaginationResponse(request);
      }
    };

    this.headerConfig3 = {
      columns: [
        { field: 'name', headerText: 'Name' },
        { field: 'gender', headerText: 'Gender' },
        { field: 'company', headerText: 'Company' }
      ],
      cardSetting: {
        cardPerRow: 5,
        template: {
          id: 'rowTemplate',
          context: {
            title: 'Dynamic Card Template',
            data: { id: 5 }
          }
        },
        cardMinSize: '300px',
        cardDataBound: cardConfig => {
          if (cardConfig.cardIndex === 0) {
            cardConfig.templateContext.extraInfo = 'This card is the first card for this page';
          }
        }
      },
      data: request => {
        return mockServerSidePaginationResponse(request);
      }
    };
  }
}
