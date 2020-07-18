import { Observable } from 'rxjs';

export class SelectConfig {
    bindLabel: string;
    bindValue?: string;
    multiple?: boolean;
    searchable?: boolean;
    searchInDropDown?: boolean;
    closeOnSelect?: boolean;
    hideSelected?: boolean;
    serverSide?: boolean;
    customPipe?: string;
    customSearchFn?: (payload: any) => void;
    data: (payload: any) => Observable<any>;
    /**
   * Refresh data according to current datatable request and redraw datatable
   */
    refresh?: () => void;
}