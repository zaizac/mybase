import {
  AfterViewInit,
  ChangeDetectionStrategy,
  Component,
  ContentChildren,
  EventEmitter,
  Input,
  OnInit,
  Output,
  QueryList,
  TemplateRef,
  ViewChild,
  ViewContainerRef,
  ViewEncapsulation
} from '@angular/core';
import {
  Column,
  DetailRowService,
  GridComponent,
  PageService,
  QueryCellInfoEventArgs,
  RowDataBoundEventArgs,
  RowDeselectEventArgs,
  RowSelectEventArgs,
  RowSelectingEventArgs,
  SelectionSettingsModel,
  TextWrapSettingsModel,
  ToolbarService
} from '@syncfusion/ej2-angular-grids';
import { L10n } from '@syncfusion/ej2-base';
import { DataManager, Query } from '@syncfusion/ej2-data';
import { BehaviorSubject } from 'rxjs';
import { first } from 'rxjs/operators';
import { CellTemplateDirective } from './cell-template.directive';
import { DatatableAdaptor } from './datatable-adoptor';
import { CardDataConfig, DatatableColumn, DatatableConfig, RowConfig } from './datatable.config';

@Component({
  selector: 'ui-datatable',
  templateUrl: './datatable.component.html',
  styleUrls: ['./datatable.component.scss'],
  providers: [DetailRowService, PageService, ToolbarService],
  encapsulation: ViewEncapsulation.None,
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class DatatableComponent implements OnInit, AfterViewInit {
  allowSorting = true;
  reorderable = true;

  // Initial Grid loading
  public initialGridLoad = true;
  public initialSettingLoad = true;

  // Syncfusion internal settings
  public toolbar: string[];
  public selectionOptions: SelectionSettingsModel;

  // For checkbox
  selectedObj: any = [];
  selectAllPages: any = [];
  isPaginationEvent = false;

  // For expand row
  expandableRow: Array<any> = [];

  // For card
  cardRowIndex = 0;
  cardRowContexts: any = {};

  // For expandable
  expandableContexts: any = {};

  // For radio button
  selectedRowData: any = null;
  // For Pager
  initialPage: any = {};
  initialPayload: any = {};
  defaultPage: any = {};
  pagerLabel: Array<any> = [];
  pageDropDownChanged = false;

  // For Editing
  editSettings: any = {};

  // Datatable request payload for server side pagination
  public requestPayload: any;
  // Submit & Reset events
  clearSorting = false;

  // Text wrapping
  public textWrap = false;
  public wrapSettings: TextWrapSettingsModel;

  // Datatable configuration
  @Input() public tableInit: DatatableConfig = {};

  @Input() public type: string;

  @ViewChild('grid')
  public grid: GridComponent;

  @Input() dataValues: any;

  dataEmitter$ = new BehaviorSubject<string>(this.dataValues);

  @Input() public rows = [];

  @Output() editRecord = new EventEmitter<Object>();

  @ViewChild(DatatableComponent) table: DatatableComponent;

  @ContentChildren(CellTemplateDirective) templateList: QueryList<any>;

  @ViewChild('datatable', { read: ViewContainerRef }) containerRef: ViewContainerRef;

  @ViewChild('dtRadioTemplate')
  private dtRadioTemplate: TemplateRef<any>;

  @ViewChild('dtExpandTemplate')
  private dtExpandTemplate: TemplateRef<any>;

  isServerSide = true;
  chkBxDisabledCnt = 0;

  constructor() {
    this.defaultPage = {
      pageSizes: [5, 10, 15, 25, 50, 100],
      pageCount: 4,
      pageSize: 15
    };

    if (!this.type) {
      this.type = '';
    }
  }

  public ngAfterViewInit(): void {
    // Angular Grid 17.3.30: Template attribute will throw error when pass it as templateRef
    // if (this.tableInit && this.tableInit.columns && this.tableInit.columns.length) {
    //   this.tableInit.columns.forEach(column => {
    //     if (column.template) {
    //       const template = column.template as any;
    //       template.elementRef.nativeElement._viewContainerRef = this.containerRef;
    //       template.elementRef.nativeElement.propName = 'template';
    //     }
    //   });
    // }
  }

  public ngOnInit(): void {
    if (this.dataValues && Array.isArray(this.dataValues)) {
      this.isServerSide = false;
    }
    // If there is no datatable config, create an empty one
    if (!this.tableInit) {
      this.tableInit = new DatatableConfig();
    }

    if (this.tableInit.toolbar) {
      this.toolbar = this.tableInit.toolbar;
    }

    if (this.tableInit.editSettings) {
      this.editSettings.allowAdding = this.tableInit.editSettings.allowAdding || false;
      this.editSettings.allowDeleting = this.tableInit.editSettings.allowDeleting || false;
      this.editSettings.allowEditing = this.tableInit.editSettings.allowEditing || false;

      if (this.tableInit.editSettings.newRowPosition) {
        this.editSettings.newRowPosition = this.tableInit.editSettings.newRowPosition;
      }

      if (this.tableInit.editSettings.mode) {
        this.editSettings.mode = this.tableInit.editSettings.mode;
      }
    } else {
      this.editSettings.allowAdding = false;
      this.editSettings.allowDeleting = false;
      this.editSettings.allowEditing = false;
    }

    if (this.tableInit.columns) {
      this.tableInit.columns.forEach(column => {
        if (column.type && (column.type === 'checkbox' || column.type === 'radio' || column.type === 'expand')) {
          column.customAttributes = { class: 'custom-icon-td' };
        }
      });

      const hasCheckboxType = !!this.tableInit.columns.find(col => col.type === 'checkbox');
      const radioButtonCol = this.tableInit.columns.find(col => col.type === 'radio');

      if (hasCheckboxType && radioButtonCol) {
        radioButtonCol.visible = false;
      }
    }

    if (this.tableInit.textWrap) {
      this.textWrap = this.tableInit.textWrap.allowWrap;
      this.wrapSettings = { wrapMode: this.tableInit.textWrap.mode };
    }

    this.tableInit.refresh = () => {
      this.grid.refreshHeader();
      this.refresh();
    };

    this.tableInit.reload = () => {
      this.grid.refreshHeader();
      this.reload();
    };

    this.tableInit.redraw = () => {
      if (this.isServerSide) {
        this.grid.refreshHeader();
        this.requestPayload.draw++;
        this.processData(this.tableInit.data(this.requestPayload));
      } else {
        this.grid.refreshHeader();
        this.grid.refresh();
      }
    };

    if (this.tableInit.selectionOptions) {
      this.selectionOptions = this.tableInit.selectionOptions;
    }

    // Set initial pagination setting
    if (this.tableInit && this.tableInit.initialPage) {
      this.initialPage.pageSize = this.tableInit.initialPage.pageSize
        ? this.tableInit.initialPage.pageSize
        : this.defaultPage.pageSize;
      this.initialPage.pageSizes =
        this.tableInit.initialPage.pageSizes === null
          ? this.defaultPage.pageSizes
          : this.tableInit.initialPage.pageSizes;
      this.initialPage.pageCount = this.tableInit.initialPage.pageCount
        ? this.tableInit.initialPage.pageCount
        : this.defaultPage.pageCount;
    } else {
      this.initialPage = this.defaultPage;
    }

    // Create datatable request payload
    this.requestPayload = this.setdataTableRequest(this.tableInit.columns);

    // Make a shallow copy of initial payload for reset from initial request payload
    this.initialPayload = Object.assign({}, this.requestPayload);

    if (this.tableInit.prevTblSettings) {
      this.initialPage.pageSize = this.tableInit.prevTblSettings.pageSize;
      this.requestPayload.start =
        (this.tableInit.prevTblSettings.currentPage - 1) * this.tableInit.prevTblSettings.pageSize;
      this.requestPayload.length = this.tableInit.prevTblSettings.pageSize;
    }

    // Execute Callback function for data if any
    if (this.tableInit.data) {
      this.processData(this.tableInit.data(this.requestPayload));
    } else {
      this.processData(this.dataValues);
    }
  }

  refresh() {
    this.addSearchToRequest(this.requestPayload, this.tableInit.columns);
    this.requestPayload.start = 0;
    this.requestPayload.draw += 1;
    if (this.tableInit.data) {
      this.processData(this.tableInit.data(this.requestPayload));
    }
  }

  reload() {
    const draw = this.requestPayload.draw + 1;
    this.requestPayload = Object.assign({}, this.initialPayload);
    if (this.tableInit.searchForm) {
      const searchKey = Object.keys(this.tableInit.searchForm);
      for (const key of searchKey) {
        this.requestPayload[key] = '';
      }
      if (this.tableInit.columns && this.tableInit.columns.length && !this.tableInit.colSearch) {
        this.tableInit.columns.forEach((column: any, index: number) => {
          if (this.tableInit.searchForm[column.field]) {
            const dtReqValuekey = 'columns[' + index + '][search][value]';
            this.requestPayload[dtReqValuekey] = '';
          }
        });
      }
    }
    this.requestPayload.draw = draw;
    this.requestPayload.length = this.initialPayload.length;
    this.clearSorting = true;
    if (this.tableInit.data) {
      this.processData(this.tableInit.data(this.requestPayload));
    }
  }

  public setRows(rows: any) {
    // update the rows
    this.rows = rows;
    // Whenever the filter changes, always go back to the first page
    this.table = rows;
  }

  // Set Syncfusion Grid configuration for initial load
  // Set Datatable localisation
  load() {
    if (this.initialSettingLoad) {
      this.initialSettingLoad = false;

      if (this.tableInit && this.tableInit.displayText && this.tableInit.displayText.pagerDropDown) {
        if (this.tableInit.displayText.pagerDropDown.includes('#pager')) {
          this.pagerLabel = this.tableInit.displayText.pagerDropDown.split('#pager');
        }
        if (!this.pagerLabel.length) {
          this.grid.pageSettings.pageSizes = false;
        }
      }
      const eleId = this.grid.element.id;
      L10n.load({
        [eleId]: {
          grid: {
            EmptyRecord:
              this.tableInit.displayText && this.tableInit.displayText.emptyRecord
                ? this.tableInit.displayText.emptyRecord
                : 'No data available in table'
          },
          pager: {
            pagerDropDown:
              this.tableInit.displayText && this.tableInit.displayText.pagerDropDown && this.pagerLabel.length > 0
                ? this.pagerLabel[1]
                : 'record per page',
            currentPageInfo:
              this.tableInit.displayText && this.tableInit.displayText.currentPageInfo
                ? this.tableInit.displayText.currentPageInfo
                : 'Showing page {0} of {1}',
            totalItemsInfo:
              this.tableInit.displayText && this.tableInit.displayText.totalItemsInfo
                ? this.tableInit.displayText.totalItemsInfo
                : ''
          }
        }
      });
      this.grid.localeObj.setLocale(eleId);
      if (this.grid.pagerModule.pagerObj && this.grid.pagerModule.pagerObj.locale) {
        this.grid.pagerModule.pagerObj.locale = eleId;
      }

      if (this.tableInit.prevTblSettings) {
        this.grid.pageSettings.currentPage = this.tableInit.prevTblSettings.currentPage;
      }
    }
  }

  // Intercept sorting, pager dropdown and pagination events for server side pagination
  onActionBegin(event) {
    this.grid.toolbar = this.tableInit.toolbar;
    this.chkBxDisabledCnt = 0;

    if (this.clearSorting) {
      this.clearSorting = false;
      this.grid.sortModule.clearSorting();
    }

    if (event.requestType === 'paging' && this.isServerSide) {
      event.cancel = true;
      this.isPaginationEvent = true;
      this.requestPayload.start = (event.currentPage - 1) * this.grid.pageSettings.pageSize;
      this.requestPayload.draw += 1;
      if (this.tableInit.data) {
        this.processData(this.tableInit.data(this.requestPayload));
      }
    }

    if (event.requestType === 'sorting' && this.isServerSide) {
      const column = this.grid.getColumnByField(event.columnName);
      if (column) {
        this.grid.pageSettings.currentPage = 1;
        this.requestPayload.start = 0;
        this.requestPayload['order[0][column]'] = column.index;
        this.requestPayload['order[0][dir]'] = event.direction === 'Ascending' ? 'asc' : 'desc';
        this.requestPayload.draw += 1;
        if (this.tableInit.data) {
          this.processData(this.tableInit.data(this.requestPayload));
        }
      }
    }

    // Syncfusion pagerModule trigger will always trigger the event twice as many as previous trigger and first trigger is not captured
    // This event will trigger before actual refresh event
    // Use flag to capture if event has been trigger
    this.grid.pagerModule.pagerObj.addEventListener('dropDownChanged', evt => {
      this.pageDropDownChanged = true;
      this.requestPayload.length = evt.pageSize;
    });

    // Refresh table of page drop down changed
    if (this.pageDropDownChanged) {
      this.requestPayload.start = 0;
      this.requestPayload.draw += 1;
      if (this.tableInit.data) {
        this.processData(this.tableInit.data(this.requestPayload));
      }
      this.pageDropDownChanged = false;
    }

    // Somehow first dropdown change didn't trigger dropDownChanged event
    if (this.tableInit.data && this.requestPayload.length !== this.grid.pageSettings.pageSize) {
      event.cancel = true;
      this.requestPayload.length = this.grid.pageSettings.pageSize;
      if (this.tableInit.data) {
        this.processData(this.tableInit.data(this.requestPayload));
      }
    }

    // Empty the contexts for every rendering so we have small object
    this.cardRowContexts = {};
    this.expandableContexts = {};
  }

  // Changing grid elements dom
  dataBound() {
    const tableElement = this.grid.element.parentElement;
    const isEmptyData = !!!this.grid.getCurrentViewRecords().length;
    const numbering = tableElement.getElementsByClassName('e-pagercontainer');
    const pageDropDown = tableElement.getElementsByClassName('e-pagesizes');
    const totalPages = tableElement.getElementsByClassName('e-parentmsgbar');
    const selectAll = this.grid.getHeaderTable().getElementsByClassName('e-checkselectall');
    const selectedArray = [];
    const rowArray = Array.from(this.grid.getRows());

    if (this.tableInit.initDrawCallBack && this.initialGridLoad) {
      this.tableInit.initDrawCallBack(this.grid.currentViewData);
    }

    if (this.tableInit.drawCallBack) {
      this.tableInit.drawCallBack(this.grid.currentViewData);
    }

    if (!this.selectedObj.length && this.tableInit && this.tableInit.selectedData) {
      this.selectedObj = this.tableInit.selectedData;
    }

    if (this.initialGridLoad && this.tableInit.prevTblSettings) {
      this.selectedRowData = this.tableInit.prevTblSettings.selectedRowData;
      this.selectAllPages = this.tableInit.prevTblSettings.selectedAllChkBxPages || [];
      this.selectedObj = this.tableInit.prevTblSettings.selectedData || [];
    }

    if ((this.chkBxDisabledCnt > 0 || (this.tableInit && this.tableInit.checkboxLimit)) && selectAll.length) {
      selectAll[0].nextElementSibling.classList.add('disabled-element');
    }

    if (this.type !== 'card') {
      let selectedRadioIndex = null;
      if (this.tableInit && this.tableInit.selectedRowData) {
        this.selectedRowData = this.tableInit.selectedRowData;
        selectedRadioIndex = this.findObjIndex(this.grid.currentViewData, this.tableInit.selectedRowData);
      } else {
        selectedRadioIndex = this.findObjIndex(this.grid.currentViewData, this.selectedRowData);
      }

      rowArray.forEach((row, index) => {
        const rowInd = this.processRowNum(index);
        if (selectAll.length) {
          if (this.selectAllPages.length && this.selectAllPages.indexOf(this.grid.pageSettings.currentPage) !== -1) {
            selectedArray.push(index);
          } else {
            if (
              this.selectedObj.length &&
              this.findObjIndex(this.selectedObj, this.grid.currentViewData[index]) !== -1
            ) {
              selectedArray.push(index);
            }
          }
        }

        if (index === selectedRadioIndex) {
          if (this.tableInit) {
            this.tableInit.selectedRowData = this.selectedRowData;
          }
          const radio = row.getElementsByClassName('dtRadio')[0];
          radio.setAttribute('checked', 'true');
        }

        const iconEl = row.getElementsByClassName('icon-expand')[0] || row.getElementsByClassName('icon-collapse')[0];
        if (iconEl) {
          this.expandingRow(iconEl, row, rowInd, true);
        }
      });

      if (selectAll.length && (this.selectAllPages.length || this.selectedObj.length) && selectedArray.length) {
        this.grid.selectRows(selectedArray);
      }
    }

    if (this.initialGridLoad) {
      this.initialGridLoad = false;
      const columns = this.grid.getColumns();

      if (!!!this.tableInit || !!!this.tableInit.columns) {
        this.requestPayload = this.setdataTableRequest(columns);
      }

      const header = tableElement.getElementsByClassName('e-headercell');
      if (columns.length > 0) {
        columns.forEach(col => {
          if (!col.allowSorting) {
            header[col.index].classList.add('default-cursor');
          }
        });
      }
      const dropDownDiv = document.createElement('div');
      const displayTxtDiv = document.createElement('div');
      displayTxtDiv.setAttribute('class', 'e-pagerconstant');
      const displayTxtSpan = document.createElement('span');
      displayTxtSpan.setAttribute('class', 'e-constant');
      displayTxtSpan.textContent =
        this.tableInit.displayText && this.tableInit.displayText.pagerDropDown && this.pagerLabel.length > 0
          ? this.pagerLabel[0]
          : 'Display';
      displayTxtDiv.appendChild(displayTxtSpan);
      if (pageDropDown[0]) {
        pageDropDown[0].insertBefore(displayTxtDiv, pageDropDown[0].firstChild);
        dropDownDiv.setAttribute('class', 'e-pager e-lib e-droppable my-pager');
        dropDownDiv.style.border = 'none';
        dropDownDiv.appendChild(pageDropDown[0]);
      }
      if (tableElement) {
        tableElement.insertBefore(dropDownDiv, tableElement.firstChild);
      }
      if (numbering[0]) {
        numbering[0].classList.add('pull-right');
      }
      if (totalPages[0]) {
        totalPages[0].setAttribute('style', 'float:left');
      }
    }

    if (numbering[0]) {
      isEmptyData ? numbering[0].classList.add('hide') : numbering[0].classList.remove('hide');
    }

    if (totalPages[0]) {
      isEmptyData ? totalPages[0].classList.add('hide') : totalPages[0].classList.remove('hide');
    }

    if (pageDropDown[0]) {
      isEmptyData ? pageDropDown[0].classList.add('hide') : pageDropDown[0].classList.remove('hide');
    }

    if (
      this.requestPayload.start === 0 &&
      this.grid.pageSettings.currentPage !== 1 &&
      !!!Array.isArray(this.dataValues)
    ) {
      this.grid.pageSettings.currentPage = 1;
    }

    const checkboxes = tableElement.getElementsByClassName('e-checkselect');
    if (checkboxes.length && this.reachChkbxLimit) {
      Array.from(checkboxes).forEach(box => {
        const checkedBox = box.parentElement.getElementsByClassName('e-check');
        if (!checkedBox.length) {
          box.nextElementSibling.classList.add('disabled-element');
          box.setAttribute('disabled', '');
        }
      });
    }

    if (this.type === 'card') {
      this.createTableCard(tableElement);
    }

    // Reset Card Row Index
    this.cardRowIndex = 0;

    // Reset value after data finish load in table
    this.isPaginationEvent = false;

    this.tableInit.prevTblSettings = Object.assign({}, this.tableInit.prevTblSettings, {
      currentPage: this.grid.pageSettings.currentPage,
      pageSize: this.grid.pageSettings.pageSize
    });
  }

  // Setting Datatable request payload
  private setdataTableRequest(myColumns: DatatableColumn[]): any {
    const req: any = {};
    if (myColumns) {
      for (let i = 0; i < myColumns.length; i++) {
        const dataKey = 'columns[' + i + '][data]';
        const nameKey = 'columns[' + i + '][name]';
        const searchKey = 'columns[' + i + '][searchable]';
        const orderKey = 'columns[' + i + '][orderable]';
        const valueKey = 'columns[' + i + '][search][value]';
        const regKey = 'columns[' + i + '][search][regex]';
        const orderColKey = 'order[0][column]';
        const orderDirKey = 'order[0][dir]';

        req[dataKey] = myColumns[i].field ? (myColumns[i].field === 'SlNo' ? '' : myColumns[i].field) : '';
        req[nameKey] = '';
        req[searchKey] = typeof myColumns[i].allowSearching === 'undefined' ? true : myColumns[i].allowSearching;
        req[orderKey] = typeof myColumns[i].allowSorting === 'undefined' ? true : myColumns[i].allowSorting;
        req[orderKey] = typeof myColumns[i].allowEditing === 'undefined' ? true : myColumns[i].allowEditing;
        req[valueKey] = '';
        req[regKey] = false;
        req[orderColKey] = 0;
        req[orderDirKey] = 'asc';
      }
    }

    this.addSearchToRequest(req, myColumns);
    req.draw = 1;
    req.start = 0;
    req.length =
      this.tableInit.initialPage && this.tableInit.initialPage.pageSize
        ? this.tableInit.initialPage.pageSize
        : this.initialPage.pageSize;
    req['search[value]'] = '';
    req['search[regex]'] = false;
    return req;
  }

  // Append request payload with search request
  private addSearchToRequest(req: any, myColumns: DatatableColumn[]) {
    if (this.tableInit.searchForm) {
      const searchKey = Object.keys(this.tableInit.searchForm);
      for (const key of searchKey) {
        req[key] = this.tableInit.searchForm[key].value || '';
      }
      if (myColumns && myColumns.length && !this.tableInit.colSearch) {
        myColumns.forEach((column: any, index: number) => {
          if (this.tableInit.searchForm[column.field]) {
            const dtReqValuekey = 'columns[' + index + '][search][value]';
            req[dtReqValuekey] = this.tableInit.searchForm[column.field].value || '';
          }
        });
      }
    }
    if (this.tableInit.colSearch && this.tableInit.colSearch.length > 0) {
      for (const search of this.tableInit.colSearch) {
        const dtReqValuekey = 'columns[' + search.colIndex + '][search][value]';
        req[dtReqValuekey] = search.value || '';
      }
    }
  }

  // Customise Row selecting events
  rowSelecting(args: RowSelectingEventArgs) {
    if (args.target && args.target.classList.contains('disabled-element')) {
      args.cancel = true;
      return;
    }

    const NoRowClick = args.target ? this.noClickEvent(args.target) : false;

    if (NoRowClick) {
      args.cancel = true;
    }

    let isCheckbox = !!(
      args.target &&
      args.target.tagName !== 'TD' &&
      args.target.tagName !== 'DIV' &&
      args.target.getAttribute('name') !== 'selectRadio' &&
      !this.isExpandButton(args.target) &&
      args.row &&
      args.row.getElementsByClassName('e-checkselect')
    );
    const rowIndex = this.processRowNum(Number(args.rowIndex));

    if (
      !args.target &&
      (this.tableInit.selectedData ||
        (this.tableInit.prevTblSettings &&
          (this.tableInit.prevTblSettings.selectedAllChkBxPages || this.tableInit.prevTblSettings.selectedData)))
    ) {
      isCheckbox = true;
    }

    // If user not click on checkbox, we cancel selection events default behaviour and return the event to parent with row data
    if (!isCheckbox) {
      args.cancel = true;
      if (args.target && args.target.getAttribute('name') === 'selectRadio') {
        this.tableInit.selectedRowData = args.data;
        this.selectedRowData = args.data;
        this.tableInit.prevTblSettings.selectedRowData = args.data;
      } else if (args.target && this.isExpandButton(args.target)) {
        this.expandingRow(args.target, args.row, rowIndex);
        this.updateExpandableRows(rowIndex);
      } else {
        this.editRecord.emit(args.data);
      }
    } else if (args.target && args.target.tagName === 'SPAN') {
      if (args.target.classList.contains('e-update')) {
        this.editRecord.emit(args.data);
      }
    }
  }

  // Create expanding row. Syncfusion has their own property detailRow to create expanding row. However, the value render in the template
  // always the particular row data. This expanding row provide same functionality but with more flexibility for user to pass any value.
  private expandingRow(iconEl: Element, rowEl: Element, rowIndex: number, init?: boolean) {
    const expandRow = this.expandableRow ? this.expandableRow.find(row => row.id === rowIndex) : null;
    const visibleCols = (this.grid.columns as Column[]).filter(col => (col as Column).visible === true);

    if (init) {
      if (expandRow && expandRow.isExpand) {
        this.expandExpandableIcon(iconEl);
        this.createExpandRowContainer(rowEl, visibleCols.length, rowIndex);
      }
    } else {
      const detailRow = rowEl.parentElement.getElementsByClassName('expandRowId' + rowIndex)[0] as HTMLElement;

      if (expandRow && expandRow.isExpand && detailRow) {
        this.collapseExpandableIcon(iconEl);
        detailRow.style.display = 'none';
      } else {
        this.expandExpandableIcon(iconEl);

        if (detailRow) {
          detailRow.removeAttribute('style');
        } else {
          this.createExpandRowContainer(rowEl, visibleCols.length, rowIndex);
        }
      }
    }
  }

  // customise row select events
  rowSelected(args: RowSelectEventArgs) {
    const isSelectAllCheckbox =
      args.target &&
      args.target.previousElementSibling &&
      args.target.previousElementSibling.classList.contains('e-checkselectall');
    const isCheckbox = !!(
      !isSelectAllCheckbox &&
      args.target &&
      args.target.tagName !== 'TD' &&
      args.target.getAttribute('name') !== 'selectRadio' &&
      !this.isExpandButton(args.target) &&
      args.target.classList.contains('e-check') &&
      !Array.isArray(args.data)
    );

    if (isSelectAllCheckbox) {
      this.selectAllCheckbox(true, this.grid.currentViewData);
    } else if (isCheckbox) {
      this.selectCheckbox(true, args.data, this.processRowNum(args.rowIndex) - 1);
    }
  }

  rowDeselecting(args: RowDeselectEventArgs) {
    this.grid.addEventListener('destroyed', evt => {
      args.cancel = true;
    });

    const NotCheckbox = !args.target || !(args.target && args.target.classList.contains('e-check'));

    if (NotCheckbox) {
      args.cancel = true;
    }
  }
  // customise row deselect events
  rowDeselected(args: RowDeselectEventArgs) {
    this.grid.addEventListener('destroyed', evt => {
      args.cancel = true;
      return;
    });

    if (args.target === undefined) {
      args.cancel = true;
      return;
    }

    const isSelectAllCheckbox =
      args.target &&
      args.target.previousElementSibling &&
      args.target.previousElementSibling.classList.contains('e-checkselectall') &&
      !this.isPaginationEvent &&
      this.selectAllPages.indexOf(this.grid.pageSettings.currentPage) !== -1;
    const isCheckbox =
      args.target &&
      (args.target.classList.contains('e-check') || args.target.classList.contains('e-uncheck')) &&
      !this.isPaginationEvent;
    if (isSelectAllCheckbox) {
      this.selectAllCheckbox(false, this.grid.currentViewData);
    } else if (isCheckbox) {
      this.selectCheckbox(false, args.data[0], this.processRowNum(args.rowIndex[0]) - 1);
    }
    args.cancel = true;
  }

  // Intercept syncfusion QueryCellInfoEventArgs for rendering
  customiseRowCells(args: QueryCellInfoEventArgs) {
    const rowIdx = Number(args.cell.getAttribute('index'));
    const extraConfig: RowConfig = {};

    const hasCheckboxType = this.tableInit.columns
      ? !!this.tableInit.columns.find(col => col.type === 'checkbox')
      : false;

    if (args.column.field === 'SlNo') {
      args.cell.textContent = String(this.processRowNum(rowIdx));
    }
    const column = args.column as DatatableColumn;

    if (!hasCheckboxType && column.type && column.type === 'radio') {
      args.cell.classList.add('custom-icon-td');
      args.cell.appendChild(this.dtRadioTemplate.createEmbeddedView(null).rootNodes[0]);
    }

    if (column.type && column.type === 'checkbox') {
      args.cell.classList.add('custom-icon-td');
    }

    if (column.render) {
      column.render(args.data[args.column.field], args.cell, args.data, extraConfig);
    }

    if (this.tableInit.customiseRowCells) {
      this.tableInit.customiseRowCells(args);
    }

    if (column.type !== 'expand' && column.columnTemplate && this.containerRef) {
      const template = this.templateList.find(list => list.cellTemplate.id === column.columnTemplate.id);
      if (template) {
        args.cell.appendChild(
          this.containerRef.createEmbeddedView(
            template.templateRef,
            {
              item: column.columnTemplate.context
            },
            0
          ).rootNodes[0]
        );
      }
    }

    if (column.type && column.type === 'expand') {
      args.cell.classList.add('custom-icon-td');
      args.cell.appendChild(
        this.containerRef.createEmbeddedView(this.dtExpandTemplate, {
          item: {
            buttonType: 'success',
            icon: 'fa fa-plus-circle'
          }
        }).rootNodes[0]
      );
      this.expandableContexts[this.processRowNum(rowIdx)] = column.columnTemplate
        ? column.columnTemplate.context
        : null;

      if (extraConfig.isDisabled) {
        const expandBtn = args.cell.firstElementChild;
        if (expandBtn) {
          expandBtn.classList.add('disabled-element');
        }
      }

      if (extraConfig.isExpand) {
        const found = this.expandableRow.find(row => row.id === this.processRowNum(rowIdx));
        if (!found) {
          this.expandableRow.push({
            id: this.processRowNum(rowIdx),
            isExpand: true
          });
        }
      }
    }

    if (column.type && extraConfig.isDisabled) {
      const colInput = args.cell.getElementsByTagName('input');
      if (colInput.length) {
        colInput[0].disabled = true;
        if (column.type === 'radio') {
          colInput[0].classList.add('not-allowed');
        } else if (column.type === 'checkbox') {
          this.chkBxDisabledCnt++;
          const colSpan = args.cell.getElementsByTagName('span');
          if (colSpan.length) {
            colSpan[0].classList.add('disabled-element');
          }
        }
      }
    }
  }

  // Intercept syncfusion RowDataBoundEventArgs for rendering
  rowDataBound(args: RowDataBoundEventArgs) {
    if (this.tableInit.rowDataBound) {
      this.tableInit.rowDataBound(args);
    }
  }

  // Intercept syncfusion RowDataBoundEventArgs for rendering. This is specific for type card
  cardRowDataBound(args: RowDataBoundEventArgs) {
    const cardConfig = new CardDataConfig();

    if (this.tableInit.cardSetting && this.tableInit.cardSetting.template) {
      cardConfig.cardIndex = this.cardRowIndex;
      cardConfig.templateId = this.tableInit.cardSetting.template.id;
      cardConfig.templateContext = Object.assign({}, this.tableInit.cardSetting.template.context, { data: args.data });
    }
    if (this.tableInit.cardSetting && this.tableInit.cardSetting.cardDataBound) {
      this.tableInit.cardSetting.cardDataBound(cardConfig);
    }
    this.cardRowContexts[this.processRowNum(this.cardRowIndex)] = cardConfig;
    this.cardRowIndex++;
  }

  // process row numbering
  processRowNum(index: number) {
    return (this.grid.pageSettings.currentPage - 1) * this.grid.pageSettings.pageSize + index + 1;
  }

  // Syncfusion already provide UI for select all checkbox. We just need to capture the value into our variable
  selectAllCheckbox(isCheck: boolean, data: any) {
    if (data && data.length) {
      data.forEach((rowData, index) => {
        if (isCheck) {
          if (this.findObjIndex(this.selectedObj, rowData) === -1) {
            this.selectedObj.push(rowData);
          }
        } else {
          this.selectedObj.splice(this.findObjIndex(this.selectedObj, rowData), 1);
        }
      });

      if (isCheck) {
        if (this.selectAllPages.indexOf(this.grid.pageSettings.currentPage) === -1) {
          this.selectAllPages.push(this.grid.pageSettings.currentPage);
        }
      } else {
        this.selectAllPages.splice(this.selectAllPages.indexOf(this.grid.pageSettings.currentPage), 1);
      }
    }
    this.tableInit.selectedData = this.selectedObj;
    this.tableInit.prevTblSettings.selectedAllChkBxPages = this.selectAllPages;
    this.tableInit.prevTblSettings.selectedData = this.selectedObj;
  }

  selectCheckbox(isCheck: boolean, data: any, rowIndex: number) {
    if (data) {
      if (isCheck) {
        const checkboxHeader = this.grid.element.getElementsByClassName('e-headerchkcelldiv');
        if (
          checkboxHeader.length &&
          checkboxHeader[0].getElementsByClassName('e-check').length === this.grid.currentViewData.length
        ) {
          if (this.selectAllPages.indexOf(this.grid.pageSettings.currentPage) === -1) {
            this.selectAllPages.push(this.grid.pageSettings.currentPage);
          }
        }
        if (this.findObjIndex(this.selectedObj, data) === -1) {
          this.selectedObj.push(data);
        }
      } else {
        this.selectAllPages.splice(this.selectAllPages.indexOf(this.grid.pageSettings.currentPage), 1);
        this.selectedObj.splice(this.findObjIndex(this.selectedObj, data), 1);
      }
    }
    this.tableInit.selectedData = this.selectedObj;
    this.tableInit.prevTblSettings.selectedAllChkBxPages = this.selectAllPages;
    this.tableInit.prevTblSettings.selectedData = this.selectedObj;

    if (this.reachChkbxLimit && isCheck) {
      this.grid.getRows().forEach(row => {
        const checkbox = row.getElementsByClassName('e-checkselect');
        if (checkbox.length) {
          const selectedBox = row.getElementsByClassName('e-check');
          if (!selectedBox.length) {
            checkbox[0].nextElementSibling.classList.add('disabled-element');
            checkbox[0].setAttribute('disabled', '');
          }
        }
      });
    }

    if (
      this.tableInit &&
      this.tableInit.checkboxLimit &&
      !(this.selectedObj.length >= this.tableInit.checkboxLimit) &&
      !isCheck
    ) {
      this.grid.getRows().forEach(row => {
        const checkbox = row.getElementsByClassName('e-checkselect');
        if (checkbox.length) {
          const selectedBox = row.getElementsByClassName('e-check');
          if (!selectedBox.length) {
            checkbox[0].nextElementSibling.classList.remove('disabled-element');
            checkbox[0].removeAttribute('disabled');
          }
        }
      });
    }
  }

  // Process Data from serverside or plain list into observable
  processData(data?: any) {
    if (data && !!!Array.isArray(data)) {
      data.pipe(first()).subscribe(
        newData => {
          if (newData != null) {
            this.dataValues = new DataManager({
              json: [JSON.stringify(newData)],
              adaptor: new DatatableAdaptor()
            }).executeLocal(new Query());
          } else {
            this.dataValues = [];
          }
          this.tableInit.serverSideResp = newData;
          this.dataEmitter$.next(this.dataValues);
        },
        error => {
          console.error(error);
        }
      );
    } else {
      this.dataValues = data || [];
      this.dataEmitter$.next(this.dataValues);
    }
  }

  isExpandButton(el: Element): boolean {
    if (el.getAttribute('name') === 'expandButton') {
      return true;
    }

    const parent = el.parentElement;
    if (parent.getAttribute('name') === 'expandButton') {
      return true;
    }

    const grandParent = parent.parentElement;
    if (grandParent.getAttribute('name') === 'expandButton') {
      return true;
    }

    return false;
  }

  private noClickEvent(el: Element): boolean {
    let parent = el;
    // Traverse maximum 10 parents node
    for (let i = 0; i < 10; i++) {
      if (!parent) {
        return false;
      }

      if (parent.getAttribute('no-row-click')) {
        return true;
      }

      parent = parent.parentElement;
    }
    return false;
  }

  private createExpandRowContainer(row: Element, colspan: number, index: number) {
    const tr = document.createElement('tr');
    tr.classList.add('expandRowId' + index);
    const td = document.createElement('td');
    td.setAttribute('colspan', String(colspan));
    const div = document.createElement('div');
    div.classList.add('expand-padding');
    tr.appendChild(td);

    const columnDef = this.tableInit.columns.find(column => column.type === 'expand');
    if (columnDef && columnDef.columnTemplate) {
      const template = this.templateList.find(list => list.cellTemplate.id === columnDef.columnTemplate.id);
      td.appendChild(div).appendChild(
        this.containerRef.createEmbeddedView(
          template.templateRef,
          {
            item: this.expandableContexts[index]
          },
          0
        ).rootNodes[0]
      );
    }
    if (row.nextSibling) {
      row.parentNode.insertBefore(tr, row.nextSibling);
    } else {
      row.parentNode.appendChild(tr);
    }
  }

  private removeExpandRowContainer(row: Element, index: number) {
    const parentEl = row.parentElement;
    const elementToBeRemoved = parentEl.getElementsByClassName('expandRowId' + index)[0];
    parentEl.removeChild(elementToBeRemoved);
  }

  private expandExpandableIcon(target: Element) {
    if (target.tagName === 'I') {
      target.classList.remove('fa-plus-circle', 'icon-expand');
      target.classList.add('fa-minus-circle', 'icon-collapse');
    } else {
      const i = target.getElementsByTagName('I')[0];
      if (i) {
        i.classList.remove('fa-plus-circle', 'icon-expand');
        i.classList.add('fa-minus-circle', 'icon-collapse');
      }
    }
  }
  private collapseExpandableIcon(target: Element) {
    if (target.tagName === 'I') {
      target.classList.remove('fa-minus-circle', 'icon-collapse');
      target.classList.add('fa-plus-circle', 'icon-expand');
    } else {
      const i = target.getElementsByTagName('I')[0];
      if (i) {
        i.classList.remove('fa-minus-circle', 'icon-collapse');
        i.classList.add('fa-plus-circle', 'icon-expand');
      }
    }
  }

  private updateExpandableRows(rowIndex: number) {
    if (!this.expandableRow.length) {
      this.expandableRow.push({
        id: rowIndex,
        isExpand: true
      });
    } else {
      const found = this.expandableRow.find(row => row.id === rowIndex);

      if (found) {
        const idx = this.expandableRow.indexOf(found);
        this.expandableRow[idx].isExpand = !this.expandableRow[idx].isExpand;
      } else {
        this.expandableRow.push({
          id: rowIndex,
          isExpand: true
        });
      }
    }
  }

  private gridTableContainer(n: number): Element {
    const divRow = document.createElement('tr');
    divRow.setAttribute('role', 'row');
    divRow.setAttribute('aria-rowindex', '0');
    divRow.setAttribute('data-uid', `grid-row0`);
    const divCol = document.createElement('td');
    divCol.classList.add('grid-container');
    divCol.setAttribute('role', 'gridcell');
    divCol.setAttribute('tabindex', '-1');
    divCol.setAttribute('aria-colindex', `0`);
    divCol.setAttribute('index', `0`);
    divCol.style.setProperty('--total', String(n));
    divCol.style.setProperty(
      '--cardMin',
      this.tableInit.cardSetting && this.tableInit.cardSetting.cardMinSize
        ? this.tableInit.cardSetting.cardMinSize
        : '21.875rem'
    );
    divRow.appendChild(divCol);

    return divRow;
  }
  private createDivCol(index?: number, width?: number): Element {
    const div = document.createElement('div');
    div.classList.add('colDiv-' + index, 'table-card');
    return div;
  }

  private createTableCard(tableElement: HTMLElement) {
    const n =
      this.tableInit.cardSetting && this.tableInit.cardSetting.cardPerRow ? this.tableInit.cardSetting.cardPerRow : 1;
    const tableContent = tableElement.getElementsByClassName('e-content')[0];
    const tableBody = tableContent.getElementsByTagName('tbody')[0];
    const details = [];
    if (this.tableInit.columns) {
      this.tableInit.columns.forEach(column => {
        if (column.field) {
          details.push({
            key: column.field,
            header: column.headerText ? column.headerText : column.field,
            value: null
          });
        }
      });
    }

    while (tableBody.firstChild) {
      tableBody.firstChild.remove();
    }

    const newCardContainerRow = this.gridTableContainer(n);
    tableBody.appendChild(newCardContainerRow);
    this.grid.currentViewData.forEach((data, idx) => {
      const index = this.processRowNum(idx);
      const newCardContainerCol = this.createDivCol(index - 1);
      if (this.tableInit.cardSetting.template) {
        const cardDetail = Object.assign({}, this.cardRowContexts[index]);
        if (this.tableInit.cardSetting.template && this.containerRef && cardDetail) {
          const template = this.templateList.find(list => list.cellTemplate.id === cardDetail.templateId);
          if (template) {
            newCardContainerCol.appendChild(
              this.containerRef.createEmbeddedView(
                template.templateRef,
                {
                  item: cardDetail.templateContext
                },
                0
              ).rootNodes[0]
            );
          }
        }
      }

      // Since we are using grid display, need to add 2 empty div for padding after last column of each row
      if (n !== 1 && idx !== 0 && (idx + 1) % this.tableInit.cardSetting.cardPerRow === 1) {
        newCardContainerRow.firstChild.appendChild(document.createElement('div'));
        newCardContainerRow.firstChild.appendChild(document.createElement('div'));
      }
      newCardContainerRow.firstChild.appendChild(newCardContainerCol);

      // When the number of card per row is 1, need to add the 2 empty div after each column
      if (n === 1 && idx !== this.grid.currentViewData.length - 1) {
        newCardContainerRow.firstChild.appendChild(document.createElement('div'));
        newCardContainerRow.firstChild.appendChild(document.createElement('div'));
      }
    });

    const gridHeader = tableElement.getElementsByClassName('e-gridheader');
    if (gridHeader.length) {
      (gridHeader[0] as HTMLElement).style.display = 'none';
    }
  }

  onTableDestroyed(obj: any) {
    // When Datatable got destroy, deselecting event is called. Need to cancel.
    this.grid.addEventListener('rowDeselecting', evt => {
      evt.cancel = true;
    });
    this.grid.addEventListener('rowDeselected', evt => {
      evt.cancel = true;
    });
  }

  findObjIndex(arrayData: any[], data: any) {
    if (!data) {
      return null;
    }

    return arrayData.findIndex(viewData => this.isObjEquivalent(viewData, data));
  }

  isObjEquivalent(obj1, obj2) {
    return JSON.stringify(obj1) === JSON.stringify(obj2);
  }

  get reachChkbxLimit(): boolean {
    return !!(
      this.tableInit &&
      this.tableInit.checkboxLimit &&
      this.selectedObj.length >= this.tableInit.checkboxLimit
    );
  }
}
