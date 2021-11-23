import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, map, Observable } from 'rxjs';
import { Parlament } from './parlament-class';
import { IParlament } from './parlament-interface';

@Injectable()
export class ParlamentService{

  constructor(private http:HttpClient) { }

  public getParlaments(): Observable<IParlament[]> {
    return this.http.get<IParlament[]>('https://data.parliament.scot/api/members')
    .pipe(
        map((response) => {
          let parlaments:IParlament[]= [];
          response.forEach((element) => {
            parlaments.push(new Parlament(element.PersonID, element.GenderTypeID, element.ParliamentaryName, element.PhotoURL, element.BirthDate, element.IsCurrent));
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

  public getMemberParties(): Observable<any> {
    return this.http.get('https://data.parliament.scot/api/memberparties');
  }

  public getParties(): Observable<any> {
    return this.http.get('https://data.parliament.scot/api/parties');
  }

  public getWebsites(): Observable<any> {
    return this.http.get('https://data.parliament.scot/api/websites');
  }
}
