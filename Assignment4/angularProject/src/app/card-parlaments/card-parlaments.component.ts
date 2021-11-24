import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
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

  parlament:IParlament=new Parlament(-1,-1,"","","",false);
  /*websites:string[];
  party={
    "party":"",
    "startDate":""
  }*/

  constructor(private parlamentService:ParlamentService, private activatedroute:ActivatedRoute) {
  }

  ngOnInit(): void {
    this.activatedroute.paramMap.subscribe(params => {
      let id:any = params.get('id');
      this.parlamentService.getParlamentsByID(+id)
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
    });
  }
}

      /*this.parlamentService.getMemberParties()
        .subscribe(
          {
            next: (response) => {
              response.forEach((element: { PersonID: number; PartyID: number}) => {
                if(element.PersonID === +id) {

                  this.parlamentService.getParties()
                    .subscribe(
                      {
                        next: (response) => {
                          response.forEach((p: { ID: number; ActualName :string, ValidFromDate: string}) => {
                            if(p.ID === element.PartyID) {
                              this.party["party"]=p.ActualName;
                              this.party["startDate"]=p.ValidFromDate;
                            }
                          });
                        },
                        error: (error) => {
                          console.log(error);
                        }
                      }
                  )

                }
              });
            },
            error: (error) => {
              console.log(error);
            }
          }
      )


      this.parlamentService.getWebsites()
        .subscribe(
          {
            next: (response) => {
              response.forEach((element: { PersonID: number; WebURL: string; }) => {
                if(element.PersonID === +id) {
                  this.websites.push(element.WebURL);
                }
              });
            },
            error: (error) => {
              console.log(error);
            }
          }
      )
    });

  }

}*/