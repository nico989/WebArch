import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { IParlament } from '../parlament-interface';
import { ParlamentService } from '../parlament.service';

@Component({
  selector: 'app-list-parlaments',
  templateUrl: './list-parlaments.component.html',
  styleUrls: ['./list-parlaments.component.css'],
  providers: []
})
export class ListParlamentsComponent implements OnInit {

  private _parlaments:IParlament[]=[];

  constructor(private parlamentService:ParlamentService, private router:Router) {
    //this.parlaments=[];
  }

  ngOnInit(): void {
    this.parlamentService.getParlaments()
      .subscribe(
        {
          next: (response) => {
            console.log(response);
            this._parlaments=response;
            this._parlaments.sort((a:IParlament,b:IParlament)=>a.ParliamentaryName.localeCompare(b.ParliamentaryName));
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

  public get parlaments(): IParlament[] {
    return this._parlaments;
  }

}
