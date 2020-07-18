import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'visibleStepper',
  pure: false
})
export class VisibleStepperPipe implements PipeTransform {
  transform(value: any, args?: any): any {
    return (value as Array<any>).filter(stepper => !stepper.hide);
  }
}
