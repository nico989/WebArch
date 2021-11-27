import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, map, Observable, of, publishReplay, refCount, shareReplay, tap } from 'rxjs';
import { IParlament } from './parlament-interface';
import { IWebsite } from './website-interface';
import { IMemberParties } from './member-parties-interface';
import { IParties } from './parties-interface';
import { CacheService } from './cache.service';

@Injectable()
export class ParlamentService{

  private readonly urlGetParlaments='https://data.parliament.scot/api/members';
  private readonly urlGetMemberPartiesById='https://data.parliament.scot/api/memberparties';
  private readonly urlGetPartiesById='https://data.parliament.scot/api/parties';
  private readonly urlGetWebsitesById='https://data.parliament.scot/api/websites';


  constructor(private http:HttpClient, private cacheService:CacheService) {
  }

  /*public getParlaments(): Observable<IParlament[]> {
    let parlaments:IParlament[]=[];
    //console.log(this.cacheService.getParlaments());
    if(this.cacheService.getParlaments()) {
      console.log("cache");
      return of(this.cacheService.getParlaments());
    } else {
      console.log("no cache");
      this.http.get<IParlament[]>(this.urlGetParlaments).subscribe(
        resp => {
          resp.forEach(elem => {parlaments.push(this.adjustParlament(elem))
          });
        }
      );
      return of(parlaments);
    }
  }*/

  private parlaments$?: Observable<IParlament[]>;

  public getParlaments(): Observable<IParlament[]> {
    if(this.parlaments$){
      console.log("cache");
      return this.parlaments$;
    }

    console.log("no cache");
    this.parlaments$=this.http.get<IParlament[]>(this.urlGetParlaments)
    .pipe(
      tap((data) => data.map(this.adjustParlament)),
      shareReplay(1)
    );

    return this.parlaments$;
  }

  /*public getParlaments(): Observable<IParlament[]> {
    let parlaments:IParlament[]=[];
    parlaments=this.cacheService.getParlaments();
    if(parlaments.length) {
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
          this.cacheService.setParlaments(parlaments);
          return parlaments;
        }),
        catchError((error) => {
          console.error(error);
          throw error;
        })
      )
    }
  }*/

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
