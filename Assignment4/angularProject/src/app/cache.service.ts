import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { IParlament } from './parlament-interface';

@Injectable()
export class CacheService{

  parlaments:IParlament[]=[];
  constructor(){ }

  public getParlaments():IParlament[] {
    return this.parlaments;
  }

  public setParlaments(parlaments:IParlament[]) {
    this.parlaments=parlaments;
  }

  public pushParlament(parlament:IParlament) {
    this.parlaments.push(parlament);
  }

}
