import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { IParlament } from './parlament-interface';
import { ParlamentService } from './parlament.service';

@Injectable()
export class CacheService{

  parlaments:Observable<IParlament[]>;

  constructor(private parlamentService:ParlamentService){ }

  public getParlaments():Observable<IParlament[]> {
    console.log(this.parlaments);
    if (this.parlaments) {
      console.log("cache");
      return this.parlaments;
    } else {
      console.log("here");
      this.parlaments=this.parlamentService.getParlaments();
      return this.parlaments;
    }
  }

}
