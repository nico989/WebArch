/**
 * Party interface
 */

export interface IParty {
  ID:number;
  Abbreviation:string;
  ActualName:string;
  PreferredName:string;
  Notes:string;
  ValidFromDate:string;
  ValidUntilDate?:string;
  MemberParties?:string;
  PartyRoles?:string;
}
