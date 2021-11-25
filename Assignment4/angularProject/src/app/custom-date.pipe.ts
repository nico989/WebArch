import { Pipe, PipeTransform } from '@angular/core';
import { DatePipe } from '@angular/common';

@Pipe({name: 'customDate'})
export class CustomDatePipe implements PipeTransform {
  constructor(private datepipe:DatePipe){}

  transform(value: string, add?:string): any {
    if(add===undefined) {
      return this.datepipe.transform(value, 'MMMM d, y');
    }
    if(value!=null) {
      return add+" "+this.datepipe.transform(value, 'MMMM d, y');
    } else {
      return "";
    }
  }
}
