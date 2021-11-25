import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CacheService } from '../cache.service';
import { IParlament } from '../parlament-interface';
import { ParlamentService } from '../parlament.service';

@Component({
  selector: 'app-list-parlaments',
  templateUrl: './list-parlaments.component.html',
  styleUrls: ['./list-parlaments.component.css'],
  providers: [CacheService, ParlamentService]
})
export class ListParlamentsComponent implements OnInit {

  parlaments:IParlament[];

  constructor(private cacheService:CacheService, private parlamentService:ParlamentService, private router:Router) {
    this.parlaments=[];
  }

  ngOnInit(): void {
    this.parlamentService.getParlaments()
      .subscribe(
        {
          next: (response) => {
            this.parlaments=response;
            this.parlaments.sort((a:IParlament,b:IParlament)=>a.ParliamentaryName.localeCompare(b.ParliamentaryName));
          },
          error: (error) => {
            console.log(error);
          }
        }
    )
  }

  public cardClicked(id:number) {
    this.router.navigate(['cardParlament', id]);
  }

}
