import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { IMemberParties } from '../models/member-parties-interface';
import { IMember } from '../models/member-interface';
import { IWebsite } from '../models/website-interface';
import { ParliamentService } from '../services/parliament.service';

@Component({
  selector: 'app-card-member',
  templateUrl: './card-member.component.html',
  styleUrls: ['./card-member.component.css'],
  providers: [ParliamentService]
})
export class CardMemberComponent implements OnInit {

  private _member?:IMember;
  private _memberParties:IMemberParties[];
  private _websites:IWebsite[];

  constructor(private parliamentService:ParliamentService, private activatedroute:ActivatedRoute) {
    this._memberParties=[];
    this._websites=[];
  }

  ngOnInit(): void {
    this.activatedroute.paramMap.subscribe(params => {
      let memberId:any = params.get('id');
      this.parliamentService.getMemberById(+memberId)
      .subscribe(
        {
          next: (member) => {
            this._member=member;
          },
          error: (error) => {
            console.log(error);
          }
        }
      )

      this.parliamentService.getMemberPartiesById(+memberId)
      .subscribe(
        {
          next: (memberParties) => {
            this._memberParties=memberParties;
            this._memberParties.forEach(element => {
              this.parliamentService.getPartyNameById(element.PartyID)
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

      this.parliamentService.getWebsitesById(+memberId)
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

  public get member(): IMember | undefined {
    return this._member;
  }

  public get memberParties(): IMemberParties[] {
    return this._memberParties;
  }

  public get websites(): IWebsite[] {
    return this._websites;
  }

}
