import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { IParlament } from '../parlament-interface';
import { ParlamentService } from '../parlament.service';

@Component({
  selector: 'app-list-parlaments',
  templateUrl: './list-parlaments.component.html',
  styleUrls: ['./list-parlaments.component.css'],
  providers: [ParlamentService]
})
export class ListParlamentsComponent implements OnInit {

  parlaments:IParlament[];

  constructor(private parlamentService:ParlamentService, private router:Router) {
    this.parlaments=[];
  }

  ngOnInit(): void {
    this.parlamentService.getParlaments()
      .subscribe(
        {
          next: (response) => {
            this.parlaments=response;
          },
          error: (error) => {
            console.log(error);
          }
        }
      )
  }

  public cardClicked() {
    this.router.navigate(['cardParlament']);
  }

}
