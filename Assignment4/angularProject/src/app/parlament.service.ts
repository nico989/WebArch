import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, map, Observable } from 'rxjs';
import { Parlament } from './parlament';

@Injectable()
export class ParlamentService{

  constructor(private http:HttpClient) { }

  public getParlaments(): Observable<Parlament[]> {
    return this.http.get<Parlament[]>('https://data.parliament.scot/api/members')
    .pipe(
      map((response) => {
        let parlaments:Parlament[]=[];
        response.forEach((element) => {
          let split=element.ParliamentaryName.split(",");
          let name=split[1].trim()+" "+split[0];
          parlaments.push(new Parlament(element.PersonID, element.GenderTypeID, name, element.PhotoURL, element.BirthDate, element.IsCurrent));
        });
        return parlaments;
      }),
      catchError((error) => {
        console.error(error);
        throw error;
      }
      )
    )
  }

}
