import { IParlament } from "./parlament-interface";

export class Parlament implements IParlament {

  PersonID:number;
  GenderTypeID:number
  ParliamentaryName:string;
  PhotoURL:string;
  BirthDate:string;
  IsCurrent:boolean;

  constructor(PersonID:number, GenderTypeID:number, ParliamentaryName:string, PhotoURL:string, BirthDate:string, IsCurrent:boolean) {
    this.PersonID=PersonID;
    this.GenderTypeID=GenderTypeID;
    this.ParliamentaryName=ParliamentaryName;
    this.PhotoURL=PhotoURL;
    this.BirthDate=BirthDate;
    this.IsCurrent=IsCurrent;
  }

}
