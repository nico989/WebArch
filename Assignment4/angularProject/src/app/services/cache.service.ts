import { Injectable } from '@angular/core';
import { IMemberParties } from '../models/member-parties-interface';
import { IMember } from '../models/member-interface';
import { IParty } from '../models/party-interface';
import { IWebsite } from '../models/website-interface';

@Injectable()
export class CacheService{

  private _parlaments:IMember[];
  private _memberParties:IMemberParties[];
  private _parties:IParty[];
  private _websites:IWebsite[];

  constructor(){
    this._parlaments=[];
    this._memberParties=[];
    this._parties=[];
    this._websites=[];
  }

  public get parlaments(): IMember[] {
    return this._parlaments;
  }

  public set parlaments(parlaments: IMember[]) {
    this._parlaments=parlaments;
  }

  public get websites(): IWebsite[] {
    return this._websites;
  }

  public set websites(websites: IWebsite[]) {
    this._websites=websites;
  }

  public get memberParties(): IMemberParties[] {
    return this._memberParties;
  }

  public set memberParties(memberParties: IMemberParties[]) {
    this._memberParties=memberParties;
  }

  public get parties(): IParty[] {
    return this._parties;
  }

  public set parties(parties: IParty[]) {
    this._parties=parties;
  }

  public getParlamentById(parlamentId:number): IMember | undefined {
    let parlament:IMember | undefined;
    this._parlaments.forEach(element => {
      if (element.PersonID === parlamentId) {
        parlament=element;
      }
    })
    return parlament;
  }

  public getWebsitesById(parlamentId:number): IWebsite[] {
    let websites:IWebsite[]=[];
    this._websites.filter(element => element.PersonID===parlamentId).forEach(element => {
      websites.push(element);
    });
    return websites;
  }

  public getMemberPartiesById(parlamentId:number): IMemberParties[] {
    let memberParties:IMemberParties[]=[];
    this._memberParties.filter(element => element.PersonID===parlamentId).forEach(element => {
      memberParties.push(element);
    });
    return memberParties;
  }

  public getPartyNameById(partyId:number): string {
    let partyName:string="";
    this._parties.filter(element => element.ID===partyId).forEach(element => {
      partyName=element.ActualName;
    });
    return partyName;
  }

}
