import { IParlament } from "./parlament-interface";

export class Parlament implements IParlament {

  PersonID:number;
  GenderTypeID:number
  ParliamentaryName:string;
  PhotoURL:string;
  BirthDate:string;
  IsCurrent:boolean;

  constructor(PersonID:number=NaN, GenderTypeID:number=NaN, ParliamentaryName:string="", PhotoURL:string="", BirthDate:string="", IsCurrent:boolean=true) {
    this.PersonID=PersonID;
    this.GenderTypeID=GenderTypeID
    //let split=ParliamentaryName.split(",");
    //let name=split[1].trim()+" "+split[0];
    //this.ParliamentaryName=name;
    this.ParliamentaryName=ParliamentaryName;
    if (PhotoURL == ""){
      if (GenderTypeID == 1) {
        this.PhotoURL="/assets/images/woman-icon.png";
      } else {
        this.PhotoURL="/assets/images/man-icon.png";
      }
    } else {
      this.PhotoURL=PhotoURL;
    }
    this.BirthDate=BirthDate;
    this.IsCurrent=IsCurrent;
  }

}
