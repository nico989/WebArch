import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, map, Observable, of } from 'rxjs';
import { IMember } from '../models/member-interface';
import { IWebsite } from '../models/website-interface';
import { IMemberParties } from '../models/member-parties-interface';
import { IParty } from '../models/party-interface';
import { CacheService } from './cache.service';

@Injectable()
export class ParliamentService{

  private readonly urlGetMembers='https://data.parliament.scot/api/members';
  private readonly urlGetMemberPartiesById='https://data.parliament.scot/api/memberparties';
  private readonly urlGetPartiesById='https://data.parliament.scot/api/parties';
  private readonly urlGetWebsitesById='https://data.parliament.scot/api/websites';


  constructor(private http:HttpClient, private cacheService:CacheService) {
  }

  // Use cache service
  public getMembers(): Observable<IMember[]> {
    let members:IMember[]=this.cacheService.members;
    if(members.length>0) {
      return of(members);
    } else {
      return this.http.get<IMember[]>(this.urlGetMembers)
      .pipe(
        map((response) => {
          response.forEach(element => {
            members.push(this.adjustMember(element));
          })

          //cache results
          this.cacheService.members = members;

          return members;
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
  public getMembers(): Observable<IMember[]> {
    return this.http.get<IMember[]>(this.urlGetMembers)
      .pipe(
        map((response) => {
          let members:IMember[]=[];
          response.forEach(element => {
            members.push(this.adjustMember(element));
          })
          return members;
        }),
        catchError((error) => {
          console.error(error);
          throw error;
        }),
      shareReplay(1)
    );
  }
  */

  public getMemberById(memberId:number): Observable<IMember> {
    let member:IMember | undefined=this.cacheService.getMemberById(memberId);
    if (member!=undefined) {
      return of(member);
    } else {
      return this.http.get<IMember>(this.urlGetMembers+"/"+memberId)
      .pipe(
        map((response) => {
          return this.adjustMember(response);
        }),
        catchError((error) => {
          console.error(error);
          throw error;
        })
      )
    }
  }

  public getMemberPartiesById(memberId:number): Observable<IMemberParties[]> {
    let memberParties:IMemberParties[]=this.cacheService.getMemberPartiesById(memberId);
    if (memberParties.length>0) {
      return of(memberParties);
    } else {
      return this.http.get<IMemberParties[]>(this.urlGetMemberPartiesById)
      .pipe(
        map((response) => {
          // cache results
          this.cacheService.memberParties=response;

          response.filter(element => element.PersonID===memberId).forEach(element => {
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

  public getWebsitesById(memberId:number): Observable<IWebsite[]> {
    let websites:IWebsite[]=this.cacheService.getWebsitesById(memberId);
    if(websites.length>0) {
      return of(websites);
    } else {
      return this.http.get<IWebsite[]>(this.urlGetWebsitesById)
      .pipe(
        map((response) => {
          // cache results
          this.cacheService.websites=response;

          response.filter(element => element.PersonID===memberId).forEach(element => {
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

  private adjustMember(member: IMember): IMember {
    member.ParliamentaryName=member.ParliamentaryName.replace(/,/, " ");
    if (!member.PhotoURL) {
      switch (member.GenderTypeID) {
        case 1: {
          member.PhotoURL='assets/images/woman-icon.png';
          break;
        }
        case 2: {
          member.PhotoURL='assets/images/man-icon.png';
          break;
        }
        default: {
          member.PhotoURL='';
          break;
        }
      }
    }
    return member;
  }

}
