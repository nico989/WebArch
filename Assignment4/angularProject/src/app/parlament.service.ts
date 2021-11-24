import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, map, Observable, of } from 'rxjs';
import { Parlament } from './parlament-class';
import { IParlament } from './parlament-interface';

@Injectable()
export class ParlamentService{


  constructor(private http:HttpClient) {
  }

  public getParlaments(): Observable<IParlament[]> {
    return this.http.get<IParlament[]>('https://data.parliament.scot/api/members')
    .pipe(
        map((response) => {
          let parlaments:IParlament[]=[];
          response.forEach((element) => {
            parlaments.push(new Parlament(element.PersonID, element.GenderTypeID, this.modifyName(element), this.pickPhotoURL(element), element.BirthDate, element.IsCurrent));
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

  public getParlamentsByID(id:number): Observable<IParlament> {
    return this.http.get<IParlament>(`https://data.parliament.scot/api/members/${id}`)
    .pipe(
        map((response) => {;
          return new Parlament(response.PersonID, response.GenderTypeID, this.modifyName(response), this.pickPhotoURL(response), response.BirthDate, response.IsCurrent);
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

  private modifyName(parl: IParlament): string {
    let split=parl.ParliamentaryName.split(",");
    return split[1].trim()+" "+split[0];
  }

  private pickPhotoURL(parl: IParlament): string {
    if (parl.PhotoURL==""){
      if (parl.GenderTypeID==1) {
        return "/assets/images/woman-icon.png";
      } else if (parl.GenderTypeID==2) {
        return "/assets/images/woman-icon.png";
      } else {
        return "";
      }
    } else {
      return parl.PhotoURL;
    }
  }
}
