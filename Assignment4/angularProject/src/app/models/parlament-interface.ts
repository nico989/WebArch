/**
 * Parlament interface
 */

export interface IParlament {
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
