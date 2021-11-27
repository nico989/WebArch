import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, map, Observable, of } from 'rxjs';
import { IParlament } from '../models/parlament-interface';
import { IWebsite } from '../models/website-interface';
import { IMemberParties } from '../models/member-parties-interface';
import { IParties } from '../models/parties-interface';
import { CacheService } from './cache.service';

@Injectable()
export class ParlamentService{

  private readonly urlGetParlaments='https://data.parliament.scot/api/members';
  private readonly urlGetMemberPartiesById='https://data.parliament.scot/api/memberparties';
  private readonly urlGetPartiesById='https://data.parliament.scot/api/parties';
  private readonly urlGetWebsitesById='https://data.parliament.scot/api/websites';


  constructor(private http:HttpClient, private cacheService:CacheService) {
  }

  public getParlaments(): Observable<IParlament[]> {
    let parlaments:IParlament[];
    parlaments=this.cacheService.parlaments;
    if(parlaments.length > 0) {
      console.log("cache");
      return of(parlaments);
    } else {
      console.log("no cache");
      return this.http.get<IParlament[]>(this.urlGetParlaments)
      .pipe(
        map((response) => {
          response.forEach(element => {
            parlaments.push(this.adjustParlament(element));
          })
          this.cacheService.parlaments = parlaments;
          return parlaments;
        }),
        catchError((error) => {
          console.error(error);
          throw error;
        })
      )
    }
  }

  public getParlamentsById(parlamentId:number): Observable<IParlament> {
    return this.http.get<IParlament>(this.urlGetParlaments+"/"+parlamentId)
    .pipe(
      map((response) => {
        return this.adjustParlament(response);
      }),
      catchError((error) => {
        console.error(error);
        throw error;
      })
    )
  }

  public getMemberPartiesById(parlamentId:number): Observable<IMemberParties[]> {
    return this.http.get<IMemberParties[]>(this.urlGetMemberPartiesById)
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
    return this.http.get<IParties[]>(this.urlGetPartiesById)
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
    return this.http.get<IWebsite[]>(this.urlGetWebsitesById)
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

  private adjustParlament(parlament: IParlament): IParlament {
    parlament.ParliamentaryName=parlament.ParliamentaryName.replace(/,/, " ");
    if (!parlament.PhotoURL) {
      switch (parlament.GenderTypeID) {
        case 1: {
          parlament.PhotoURL='assets/images/woman-icon.png';
          break;
        }
        case 2: {
          parlament.PhotoURL='assets/images/man-icon.png';
          break;
        }
        default: {
          parlament.PhotoURL='';
          break;
        }
      }
    }
    return parlament;
  }

}
