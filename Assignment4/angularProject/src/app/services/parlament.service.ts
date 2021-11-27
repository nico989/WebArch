import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, map, Observable, of, publishReplay, refCount, shareReplay, tap } from 'rxjs';
import { IParlament } from '../models/parlament-interface';
import { IWebsite } from '../models/website-interface';
import { IMemberParties } from '../models/member-parties-interface';
import { IParty } from '../models/party-interface';
import { CacheService } from './cache.service';

@Injectable()
export class ParlamentService{

  private readonly urlGetParlaments='https://data.parliament.scot/api/members';
  private readonly urlGetMemberPartiesById='https://data.parliament.scot/api/memberparties';
  private readonly urlGetPartiesById='https://data.parliament.scot/api/parties';
  private readonly urlGetWebsitesById='https://data.parliament.scot/api/websites';


  constructor(private http:HttpClient, private cacheService:CacheService) {
  }

  // Use custom cache
  public getParlaments(): Observable<IParlament[]> {
    let parlaments:IParlament[]=this.cacheService.parlaments;
    if(parlaments.length>0) {
      return of(parlaments);
    } else {
      return this.http.get<IParlament[]>(this.urlGetParlaments)
      .pipe(
        map((response) => {
          console.log(response);
          response.forEach(element => {
            parlaments.push(this.adjustParlament(element));
          })

          //cache results
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

  /*
  //Cache with shareReplay
  public getParlaments(): Observable<IParlament[]> {
    return this.http.get<IParlament[]>(this.urlGetParlaments)
      .pipe(
        map((response) => {
          let parlaments:IParlament[]=[];
          response.forEach(element => {
            parlaments.push(this.adjustParlament(element));
          })
          return parlaments;
        }),
        catchError((error) => {
          console.error(error);
          throw error;
        }),
      shareReplay(1)
    );
  }
  */

  public getParlamentsById(parlamentId:number): Observable<IParlament> {
    let parlament:IParlament | undefined=this.cacheService.getParlamentById(parlamentId);
    if (parlament!=undefined) {
      return of(parlament);
    } else {
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
  }

  public getMemberPartiesById(parlamentId:number): Observable<IMemberParties[]> {
    let memberParties:IMemberParties[]=this.cacheService.getMemberPartiesById(parlamentId);
    if (memberParties.length>0) {
      return of(memberParties);
    } else {
      return this.http.get<IMemberParties[]>(this.urlGetMemberPartiesById)
      .pipe(
        map((response) => {
          // cache results
          this.cacheService.memberParties=response;

          response.filter(element => element.PersonID===parlamentId).forEach(element => {
            memberParties.push(element);
          });
          return memberParties;
        }),
        catchError((error) => {
          console.error(error);
          throw error;
        })
      )
    }
  }

  public getPartyNameById(partyId:number): Observable<string> {
    let party:string=this.cacheService.getPartyNameById(partyId);
    if(party!="") {
      return of(party);
    } else {
      return this.http.get<IParty[]>(this.urlGetPartiesById)
      .pipe(
        map((response) => {
          // cache results
          this.cacheService.parties=response;

          response.filter(element => element.ID===partyId).forEach(element => {
            party=element.ActualName;
          });
          return party;
        }),
        catchError((error) => {
          console.error(error);
          throw error;
        })
      )
    }
  }

  public getWebsitesById(parlamentId:number): Observable<IWebsite[]> {
    let websites:IWebsite[]=this.cacheService.getWebsitesById(parlamentId);
    if(websites.length>0) {
      return of(websites);
    } else {
      return this.http.get<IWebsite[]>(this.urlGetWebsitesById)
      .pipe(
        map((response) => {
          // cache results
          this.cacheService.websites=response;

          response.filter(element => element.PersonID===parlamentId).forEach(element => {
            websites.push(element);
          });
          return websites;
        }),
        catchError((error) => {
          console.error(error);
          throw error;
        })
      )
    }
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
