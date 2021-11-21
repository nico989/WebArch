import { Component, OnInit } from '@angular/core';
import { Parlament } from '../parlament';
import { ParlamentService } from '../parlament.service';

@Component({
  selector: 'app-list-parlaments',
  templateUrl: './list-parlaments.component.html',
  styleUrls: ['./list-parlaments.component.css'],
  providers: [ParlamentService]
})
export class ListParlamentsComponent implements OnInit {

  parlaments:Parlament[];
  error:string

  constructor(private parlamentService:ParlamentService) {
    this.parlaments=[];
    this.error="";
  }

  ngOnInit(): void {
    this.parlamentService.getParlaments()
      .subscribe(
        {
          next: (response) => {
            this.parlaments=response;
            console.log(this.parlaments)
          },
          error: (error) => this.error=error
        }
      )
  }

}
