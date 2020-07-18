import {
  Column,
  ColumnModel,
  CommandModel,
  ICellFormatter,
  IEditCell,
  IFilter,
  IFilterUI,
  SelectionSettingsModel,
  SortComparer,
  ValueAccessor
} from '@syncfusion/ej2-angular-grids';
import { DateFormatOptions, NumberFormatOptions } from '@syncfusion/ej2-base';
import { DataManager } from '@syncfusion/ej2-data';
import { Observable } from 'rxjs';

export class DatatableConfig {
  /**
   * Define Datatable columns properties
   */
  columns?: DatatableColumn[];
  displayText?: {
    emptyRecord?: string;
    pagerDropDown?: string;
    currentPageInfo?: string;
    totalItemsInfo?: string;
  };
  initialPage?: {
    pageSizes?: boolean | number[];
    pageCount?: number;
    pageSize?: number;
  };
  /**
   * Form for searching datatable data. Expect Reactive form formgroup
   */
  searchForm?: any;
  colSearch?: { colIndex: number; value: any }[];
  customiseRowCells?: (event: any) => void;
  rowDataBound?: (event: any) => void;
  /**
   * Callback function to display datatable data.
   * @param payload - datatable return payload request for data service
   *
   */
  data?: (payload?: any) => Observable<any>;
  /**
   * Checkbox selected data. Return an Array
   */
  selectedData?: any;
  /**
   * Radio button selected data. Return single row data
   */
  selectedRowData?: any;
  selectionOptions?: SelectionSettingsModel;
  /**
   * Return datatable response from server
   */
  serverSideResp?: any;
  /**
   * Refresh data according to current datatable request and redraw datatable
   */
  refresh?: () => void;
  /**
   * Refresh data according to initial datatable request and redraw datatable
   */
  reload?: () => void;
  /**
   * redraw datatable without fetching new data
   */
  redraw?: () => void;
  /**
   * By default all text in datatable will be truncated with ellipsis. This property allow the text to be wrapped instead of truncated.
   */
  textWrap?: {
    /**
     * allowWrap: boolean - Set true to allow text wrap.
     */
    allowWrap: boolean;
    /**
     *  mode: 3 values -
     *  Both - Wrap both header and content text.
     *  Header - Wrap header text only.
     *  Content - Wrap content text only.
     */
    mode: 'Both' | 'Header' | 'Content';
  };
  /**
   * Setting for card mode
   */
  cardSetting?: {
    cardPerRow?: number;
    template: {
      id: string;
      context?: any;
    };
    cardMinSize?: string;
    cardDataBound?: (cardConfig: CardDataConfig) => void;
  };
  /**
   * Hold table history for pages, radio and checkbox selection for components with workflow
   */
  prevTblSettings?: {
    pageSize?: number;
    currentPage?: number;
    selectedData?: any;
    selectedRowData?: any;
    selectedAllChkBxPages?: any;
  };
  /**
   *  Setting for row editing
   */
  editSettings?: {
    allowEditing?: boolean;
    allowAdding?: boolean;
    allowDeleting?: boolean;
    newRowPosition?: string;
    mode?: string;
  };

  toolbar?: string[];
  selectFirstByDefault?: boolean;
  initDrawCallBack?: (currentViewData: any) => void;
  drawCallBack?: (currentViewData: any) => void;
  checkboxLimit?: number;
  constructor() {}
}

export class DatatableColumn implements ColumnModel {
  // ColumnModel interface properties //
  field?: string;
  uid?: string;
  index?: number;
  headerText?: string;
  width?: string | number;
  minWidth?: string | number;
  maxWidth?: string | number;
  textAlign?: 'Left' | 'Right' | 'Center' | 'Justify';
  clipMode?: 'Clip' | 'Ellipsis' | 'EllipsisWithTooltip';
  headerTextAlign?: 'Left' | 'Right' | 'Center' | 'Justify';
  disableHtmlEncode?: boolean;
  type?: string;
  format?: string | NumberFormatOptions | DateFormatOptions;
  visible?: boolean;
  /**
   * @deprecated
   * Angular Grid 17.3.30 will throw error when passing it as templateRef. Use uiNgTemplateCellTemplate directive instead
   */
  template?: string;
  headerTemplate?: string;
  isFrozen?: boolean;
  allowSorting?: boolean;
  allowResizing?: boolean;
  showColumnMenu?: boolean;
  allowFiltering?: boolean;
  allowGrouping?: boolean;
  allowReordering?: boolean;
  enableGroupByFormat?: boolean;
  allowEditing?: boolean;
  customAttributes?: {
    [x: string]: Object;
  };
  displayAsCheckBox?: boolean;
  dataSource?: Object[] | DataManager;
  formatter?:
    | {
        new (): ICellFormatter;
      }
    | ICellFormatter
    | Function;
  valueAccessor?: ValueAccessor | string;
  filterBarTemplate?: IFilterUI;
  filter?: IFilter;
  columns?: Column[] | string[] | ColumnModel[];
  toolTip?: string;
  isPrimaryKey?: boolean;
  editType?: string;
  validationRules?: Object;
  autoFit?: boolean;
  defaultValue?: string;
  edit?: IEditCell;
  isIdentity?: boolean;
  foreignKeyField?: string;
  foreignKeyValue?: string;
  hideAtMedia?: string;
  showInColumnChooser?: boolean;
  commandsTemplate?: string;
  commands?: CommandModel[];
  sortComparer?: SortComparer | string;
  isForeignColumn?: () => boolean;
  editTemplate?: string;
  filterTemplate?: string;
  lockColumn?: boolean;
  allowSearching?: boolean;
  // end of ColumnModel interface properties //
  render?: (data: any, cell: Element, row: { [k: string]: any }, extraConfig?: RowConfig) => void;
  columnTemplate?: {
    id: string;
    context?: any;
  };
}

export class CardDataConfig {
  templateId: string;
  templateContext: any;
  cardIndex: number;
  constructor() {}
}

export class RowConfig {
  isDisabled?: boolean;
  isExpand?: boolean;
}
