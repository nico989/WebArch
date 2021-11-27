import { Injectable } from '@angular/core';
import { IParlament } from './parlament-interface';

@Injectable()
export class CacheService{

  private _parlaments:IParlament[];

  constructor(){
    this._parlaments=[];
  }

  public get parlaments():IParlament[] {
    return this._parlaments;
  }

  public set parlaments(parlaments:IParlament[]) {
    this._parlaments=parlaments;
  }

}
