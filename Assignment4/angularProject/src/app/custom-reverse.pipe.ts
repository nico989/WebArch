import { Pipe, PipeTransform } from '@angular/core';

@Pipe({name: 'customReverse'})
export class CustomReversePipe implements PipeTransform {
    transform(value: string): string {
      let split=value.split(" ");
      return split[1]+" "+split[0];
    }
}
