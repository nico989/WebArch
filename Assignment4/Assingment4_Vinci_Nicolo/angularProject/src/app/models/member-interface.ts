/**
 * Member interface
 */

export interface IMember {
  PersonID: number;
  PhotoURL: string;
  Notes: string;
  BirthDate: string;
  BirthDateIsProtected: boolean;
  ParliamentaryName: string;
  PreferredName: string;
  GenderTypeID: number;
  IsCurrent: boolean;
}
