import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { IMemberParties } from '../member-parties-interface';
import { Parlament } from '../parlament-class';
import { IParlament } from '../parlament-interface';
import { ParlamentService } from '../parlament.service';

@Component({
  selector: 'app-card-parlaments',
  templateUrl: './card-parlaments.component.html',
  styleUrls: ['./card-parlaments.component.css'],
  providers: []
})
export class CardParlamentsComponent implements OnInit {

  parlament:IParlament;
  websites:string[];
  memberParties:IMemberParties[];

  constructor(private parlamentService:ParlamentService, private activatedroute:ActivatedRoute) {
    this.parlament=new Parlament(-1,-1,"","","",false);
    this.memberParties=[];
    this.websites=[];
  }

  ngOnInit(): void {
    this.activatedroute.paramMap.subscribe(params => {
      let parlamentId:any = params.get('id');
      this.parlamentService.getParlamentsById(+parlamentId)
      .subscribe(
        {
          next: (response) => {
            this.parlament=response;
          },
          error: (error) => {
            console.log(error);
          }
        }
      )

      this.parlamentService.getMemberPartiesById(+parlamentId)
      .subscribe(
        {
          next: (response) => {
            this.memberParties=response;
            this.memberParties.forEach(element => {
              this.parlamentService.getPartiesById(element.PartyID)
                .subscribe(
                  {
                    next: (response) => {
                      element.PartyName=response;
                    },
                    error: (error) => {
                      console.log(error);
                    }
                  }
                )
            })
          },
          error: (error) => {
            console.log(error);
          }
        }
      )

      this.parlamentService.getWebsitesById(+parlamentId)
      .subscribe(
        {
          next: (response) => {
            this.websites=response;
          },
          error: (error) => {
            console.log(error);
          }
        }
      )
    });
  }
}
