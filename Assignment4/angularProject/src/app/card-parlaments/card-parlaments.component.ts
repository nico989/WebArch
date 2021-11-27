import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { IMemberParties } from '../member-parties-interface';
import { IParlament } from '../parlament-interface';
import { ParlamentService } from '../parlament.service';

@Component({
  selector: 'app-card-parlaments',
  templateUrl: './card-parlaments.component.html',
  styleUrls: ['./card-parlaments.component.css'],
  providers: []
})
export class CardParlamentsComponent implements OnInit {

  private _parlament?:IParlament;
  private _websites:string[];
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
          next: (response) => {
            this._parlament=response;
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
            this._memberParties=response;
            this._memberParties.forEach(element => {
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
            this._websites=response;
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

  public get websites(): string[] {
    return this._websites;
  }

}
