export interface IMemberParties {
  ID: number;
  PersonID:number;
  PartyID:number;
  ValidFromDate:string;
  ValidUntilDate?:string;
  MemberPartyRoles?: string;
  PartyName:string;
}
