import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { IMemberParties } from '../models/member-parties-interface';
import { IParlament } from '../models/parlament-interface';
import { IWebsite } from '../models/website-interface';
import { ParlamentService } from '../services/parlament.service';

@Component({
  selector: 'app-card-parlaments',
  templateUrl: './card-parlaments.component.html',
  styleUrls: ['./card-parlaments.component.css'],
  providers: [ParlamentService]
})
export class CardParlamentsComponent implements OnInit {

  private _parlament?:IParlament;
  private _websites:IWebsite[];
  private _memberParties:IMemberParties[];

  constructor(private parlamentService:ParlamentService, private activatedroute:ActivatedRoute) {
    this._memberParties=[];
    this._websites=[];
  }

  ngOnInit(): void {
    this.activatedroute.paramMap.subscribe(params => {
      let parlamentId:any = params.get('id');
      this.parlamentService.getParlamentsById(+parlamentId)
      .subscribe(
        {
          next: (parlament) => {
            this._parlament=parlament;
          },
          error: (error) => {
            console.log(error);
          }
        }
      )

      this.parlamentService.getMemberPartiesById(+parlamentId)
      .subscribe(
        {
          next: (memberParties) => {
            this._memberParties=memberParties;
            this._memberParties.forEach(element => {
              this.parlamentService.getPartyNameById(element.PartyID)
                .subscribe(
                  {
                    next: (partyName) => {
                      element.PartyName=partyName;
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
          next: (websites) => {
            this._websites=websites;
          },
          error: (error) => {
            console.log(error);
          }
        }
      )
    });
  }

  public get parlament(): IParlament | undefined {
    return this._parlament;
  }

  public get memberParties(): IMemberParties[] {
    return this._memberParties;
  }

  public get websites(): IWebsite[] {
    return this._websites;
  }

}
