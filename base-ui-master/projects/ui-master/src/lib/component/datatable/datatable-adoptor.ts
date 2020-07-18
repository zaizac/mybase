import { JsonAdaptor } from '@syncfusion/ej2-data';

export class DatatableAdaptor extends JsonAdaptor {
  onSortBy(): any {
    return false;
  }

  processResponse() {
    const original = arguments[1].dataSource.json.length ? JSON.parse(arguments[1].dataSource.json) : null;
    if (!!original && original.data) {
      if (original.data.length) {
        return { result: original.data, count: original.recordsFiltered };
      }
    }

    return { result: [], count: 0 };
  }
}
