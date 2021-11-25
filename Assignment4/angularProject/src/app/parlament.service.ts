import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, map, Observable, of } from 'rxjs';
import { Parlament } from './parlament-class';
import { IParlament } from './parlament-interface';
import { IWebsite } from './website-interface';
import { IMemberParties } from './member-parties-interface';
import { IParties } from './parties-interface';

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

  public getParlamentsById(parlamentId:number): Observable<IParlament> {
    return this.http.get<IParlament>(`https://data.parliament.scot/api/members/${parlamentId}`)
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

  public getMemberPartiesById(parlamentId:number): Observable<IMemberParties[]> {
    return this.http.get<IMemberParties[]>('https://data.parliament.scot/api/memberparties')
    .pipe(
        map((response) => {
          let memberParties:IMemberParties[]=[];
          response.filter(element => element.PersonID===parlamentId).forEach(element => {
            memberParties.push(element);
          });
          return memberParties;
        }),
        catchError((error) => {
          console.error(error);
          throw error;
        }
      )
    )
  }

  public getPartiesById(partyId:number): Observable<string> {
    return this.http.get<IParties[]>('https://data.parliament.scot/api/parties')
      .pipe(
        map((response) => {
          let partyName="";
          response.filter(element => element.ID===partyId).forEach(element => {
            partyName=element.ActualName;
          });
          return partyName;
        }),
        catchError((error) => {
          console.error(error);
          throw error;
        }
      )
    )
  }

  public getWebsitesById(parlamentId:number): Observable<string[]> {
    return this.http.get<IWebsite[]>('https://data.parliament.scot/api/websites')
    .pipe(
        map((response) => {
          let websites:string[]=[];
          response.filter(element => element.PersonID===parlamentId).forEach(element => {
            websites.push(element.WebURL);
          });
          return websites;
        }),
        catchError((error) => {
          console.error(error);
          throw error;
        }
      )
    )
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
