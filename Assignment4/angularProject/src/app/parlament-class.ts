import { IParlament } from "./parlament-interface";

export class Parlament implements IParlament {

  constructor(PersonID:number, GenderTypeID:number, ParliamentaryName:string, PhotoURL:string, BirthDate:string, IsCurrent:boolean) {
    this.PersonID=PersonID;
    this.GenderTypeID=GenderTypeID
    let split=ParliamentaryName.split(",");
    let name=split[1].trim()+" "+split[0];
    this.ParliamentaryName=name;
    this.PhotoURL=PhotoURL;
    this.BirthDate=BirthDate;
    this.IsCurrent=IsCurrent;
  }

  PersonID:number;
  GenderTypeID:number
  ParliamentaryName:string;
  PhotoURL:string;
  BirthDate:string;
  IsCurrent:boolean;

}
