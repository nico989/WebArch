import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { IMemberParties } from '../member-parties';
import { Parlament } from '../parlament-class';
import { IParlament } from '../parlament-interface';
import { ParlamentService } from '../parlament.service';

@Component({
  selector: 'app-card-parlaments',
  templateUrl: './card-parlaments.component.html',
  styleUrls: ['./card-parlaments.component.css'],
  providers: [ParlamentService]
})
export class CardParlamentsComponent implements OnInit {

  parlament:IParlament;
  websites:string[];
  party:string;
  lastValidFrom:string;

  constructor(private parlamentService:ParlamentService, private activatedroute:ActivatedRoute) {
    this.parlament=new Parlament(-1,-1,"","","",false);
    this.websites=[];
    this.party="";
    this.lastValidFrom="";
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
            let memberParties:IMemberParties[]=response;
            let lastMemberParty=memberParties[memberParties.length-1];
            this.lastValidFrom=lastMemberParty.ValidFromDate;
            this.parlamentService.getPartiesById(lastMemberParty.PartyID)
              .subscribe(
                {
                  next: (response) => {
                    this.party=response;
                  },
                  error: (error) => {
                    console.log(error);
                  }
                }
              )
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
