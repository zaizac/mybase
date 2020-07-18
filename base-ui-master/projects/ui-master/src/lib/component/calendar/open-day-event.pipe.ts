import { Pipe, PipeTransform } from '@angular/core';
import { format as dateFormatter } from 'date-fns';

@Pipe({
  name: 'openDayEvent'
})
export class OpenDayEventPipe implements PipeTransform {
  transform(value: any, args?: any): any {
    const start = this.getDateTime(value.start);
    const end = this.getDateTime(value.end);
    let from = '';
    let to = '';
    if (start) {
      from = `${start}`;
    }

    if (end) {
      to = ` - ${end}`;
    }

    const dateTime = value.allDay ? `All day event` : `${from}${to}`;

    return `${value.title}\n${dateTime}`;
  }

  private getDateTime(date) {
    if (date) {
      return dateFormatter(date, 'h:mm A');
    }
    return null;
  }
}
