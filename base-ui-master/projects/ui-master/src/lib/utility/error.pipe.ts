import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'parseError'
})
export class ErrorPipe implements PipeTransform {

  transform(value: any, args?: any): any {
    if (!value) {
      return value;
    }
    return Object.keys(value);
  }

}